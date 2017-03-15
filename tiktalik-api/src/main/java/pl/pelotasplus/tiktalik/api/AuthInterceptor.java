package pl.pelotasplus.tiktalik.api;

import okhttp3.*;
import okio.Buffer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.TimeZone;

final class AuthInterceptor implements Interceptor {
    private final String apiKey, apiSecret;

    public AuthInterceptor(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String contentType = "";
        String body = "";
        String md5 = "";

        RequestBody requestBody = request.body();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            body = buffer.readUtf8();
            if (body.length() > 0) {
                contentType = "application/x-www-form-urlencoded";
                md5 = md5Digest(body);
            }
        }

        HttpUrl requestURL = request.url();
        String method = request.method();

        // date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = simpleDateFormat.format(new Date());

        // path
        String path = requestURL.encodedPath();
        if (requestURL.encodedQuery() != null && requestURL.encodedQuery().length() > 0) {
            path += "?" + requestURL.encodedQuery();
        }

        String digestString = ""
                + method + "\n"
                + md5 + "\n" // md5
                + contentType + "\n" // content type
                + date + "\n" // date
                + path;

//        System.out.println("digest string\n" + digestString);

        Request.Builder newBuilder = request.newBuilder();
        newBuilder
                .addHeader("Authorization", "TKAuth " + apiKey + ":" + hmacDigest(digestString, apiSecret))
//                                .addHeader("Accept-Encoding", "identity")
//                                .addHeader("Host", "tiktalik.com")
                .addHeader("date", date);
        if (md5.length() > 0) {
            newBuilder.addHeader("content-md5", md5);
        }

        return chain.proceed(newBuilder.build());
    }

    protected String hmacDigest(String msg, String privateKeyString) {
        try {
            final String algorithm = "HmacSHA1";

            byte[] keyBytes = Base64.getDecoder().decode(privateKeyString);
            Key key = new SecretKeySpec(keyBytes, 0, keyBytes.length, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(key);

            byte[] bytes = mac.doFinal(msg.getBytes("UTF-8"));

            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception ignored) {
        }
        return null;
    }

    protected String md5Digest(String msg) {
        try {
            byte[] bytes = MessageDigest.getInstance("MD5").digest(msg.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toHexString((aByte & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception ignored) {
        }
        return null;
    }
}
