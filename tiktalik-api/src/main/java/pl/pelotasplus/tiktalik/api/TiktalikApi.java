package pl.pelotasplus.tiktalik.api;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.pelotasplus.tiktalik.api.model.Image;
import pl.pelotasplus.tiktalik.api.model.Instance;
import pl.pelotasplus.tiktalik.api.model.Product;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

import java.util.List;

public interface TiktalikApi {
    String API_ENDPOINT = "https://tiktalik.com";

    @GET("/api/v1/computing/instance?actions=true&vpsimage=true&cost=true")
    Observable<List<Instance>> listInstances();

    @GET("/api/v1/computing/instance/{uuid}")
    Observable<Instance> getInstance(@Path("uuid") String uuid);

    @POST("/api/v1/computing/instance/{uuid}/stop")
    Observable<ResponseBody> stopInstance(@Path("uuid") String uuid);

    @FormUrlEncoded
    @POST("/api/v1/computing/instance")
    Observable<Instance> createInstance(
            @Field("hostname") String hostname,
            @Field("image_uuid") String imageUuid,
            @Field("size") String size,
            @Field("networks[]") String networks,
            @Field("ssh_key") String ssk_key,
            @Field("disk_size_gb") String diskSizeGb
    );

    @POST("/api/v1/computing/instance/{uuid}/force_stop")
    Observable<ResponseBody> forceStopInstance(@Path("uuid") String uuid);

    @POST("/api/v1/computing/instance/{uuid}/start")
    Observable<ResponseBody> startInstance(@Path("uuid") String uuid);

    @DELETE("/api/v1/computing/instance/{uuid}")
    Observable<ResponseBody> destroyInstance(@Path("uuid") String uuid);

    @GET("/api/v1/computing/product")
    Observable<List<Product>> listProducts();

    @GET("/api/v1/computing/image")
    Observable<List<Image>> listImages();

    @GET("/api/v1/computing/image/{uuid}")
    Observable<Image> getImage(@Path("uuid") String uuid);

    interface State {
        int INSTALLING = 10;
        int STOPPED = 11;
        int RUNNING = 12;
    }

    class Builder {
        final String apiKey;
        final String apiSecret;
        private HttpLoggingInterceptor.Logger logger;

        public Builder(String apiKey, String apiSecret) {
            this.apiKey = apiKey;
            this.apiSecret = apiSecret;
        }

        public Builder withLogging(HttpLoggingInterceptor.Logger logger) {
            this.logger = logger;
            return this;
        }

        public TiktalikApi build() {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.addInterceptor(new AuthInterceptor(apiKey, apiSecret));
            if (logger != null) {
                clientBuilder.addInterceptor(
                        new HttpLoggingInterceptor(logger)
                                .setLevel(HttpLoggingInterceptor.Level.BODY)
                );
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_ENDPOINT)
                    .client(clientBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit.create(TiktalikApi.class);
        }
    }
}