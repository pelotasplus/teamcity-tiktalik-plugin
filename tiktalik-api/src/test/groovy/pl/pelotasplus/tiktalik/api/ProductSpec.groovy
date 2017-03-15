package pl.pelotasplus.tiktalik.api

import spock.lang.Specification

class ProductSpec extends Specification {
    TiktalikApi api

    def "setup"() {
        api = new TiktalikApi.Builder(Common.API_KEY, Common.API_SECRET)
                .withLogging(true)
                .build()
    }

    def "should get list of products"() {
        when:
        def products = api.listProducts().blockingFirst()

        then:
        products.size() == 25
    }
}