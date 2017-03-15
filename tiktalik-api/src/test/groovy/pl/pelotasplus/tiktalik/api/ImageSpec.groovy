package pl.pelotasplus.tiktalik.api

import spock.lang.Specification

class ImageSpec extends Specification {
    TiktalikApi api

    def "setup"() {
        api = new TiktalikApi.Builder(Common.API_KEY, Common.API_SECRET)
                .withLogging(true)
                .build()
    }

    def "should get list of images"() {
        when:
        def images = api.listImages().blockingFirst()

        then:
        images.size() == 9
    }

    def "should get image details"() {
        when:
        def details = api.getImage("d8d7fc3f-d28d-425b-a90f-8e1f68db25f0").blockingFirst()

        then:
        details.name == "Centos 6.4 64-bit"
    }
}