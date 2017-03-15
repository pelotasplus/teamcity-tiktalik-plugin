package pl.pelotasplus.tiktalik.api

import spock.lang.Specification

/**
 * Created by alek on 3/10/17.
 */
class AuthInterceptorSpec extends Specification {
    AuthInterceptor authInterceptor
    def apiKey = "apiKey"
    def apiSecret = "apiSecret"

    def "setup"() {
        authInterceptor = new AuthInterceptor(apiKey, apiSecret)
    }

    def "should sign"() {
        given:
        def msg = "GET\n" +
                "\n" +
                "\n" +
                "Fri, 10 Mar 2017 17:36:00 GMT\n" +
                "/api/v1/computing/image"

        expect:
        authInterceptor.hmacDigest(msg, "ULQ/dVl0JWsrP/J1fEREIkn7DCE=") == "N26Bivws5NjCr+ZyJE9Ljeb6ZzM="
    }

    def "should calculate md5"() {
        given:
        def msg = "image_uuid=d8d7fc3f-d28d-425b-a90f-8e1f68db25f0&hostname=test1&size=1s"

        expect:
        authInterceptor.md5Digest(msg) == "b74a146ff6f84c4507d7e19ce6616102"
    }

    def "should calculate md5 2"() {
        given:
        def msg = "hostname=test9&image_uuid=d8d7fc3f-d28d-425b-a90f-8e1f68db25f0&size=1s&networks%5B%5D=auto&ssh_key=&disk_size_gb=20"

        expect:
        authInterceptor.md5Digest(msg) == "e68e71ca31fef12fd5ad53a03fdfc55b"
    }
}
