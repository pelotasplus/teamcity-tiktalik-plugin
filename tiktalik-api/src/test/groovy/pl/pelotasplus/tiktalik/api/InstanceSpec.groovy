package pl.pelotasplus.tiktalik.api

import pl.pelotasplus.tiktalik.api.model.Instance
import spock.lang.Specification

import java.util.concurrent.TimeUnit

class InstanceSpec extends Specification {
    TiktalikApi api

    def "setup"() {
        api = new TiktalikApi.Builder(Common.API_KEY, Common.API_SECRET)
                .withLogging(true)
                .build()
    }

    def "should create instance"() {
        given:
        def instance = api.createInstance(
                "test9",
                "d8d7fc3f-d28d-425b-a90f-8e1f68db25f0",
                "1s",
                "auto",
                "",
                "20"
        ).blockingFirst()

        when:
        TimeUnit.SECONDS.sleep(90)

        and:
        def instances = api.listInstances().blockingFirst()

        then:
        instances.size() == 1

        and:
        instances[0].state == TiktalikApi.State.RUNNING
    }


    def "should stop instance"() {
        given:
        def instances = api.listInstances().blockingFirst()

        and:
        def instance = instances[0] as Instance

        when:
        api.forceStopInstance(instance.uuid).blockingFirst()

        and:
        TimeUnit.SECONDS.sleep(30)

        and:
        def instance2 = api.getInstance(instance.uuid).blockingFirst()

        then:
        instance2.state == TiktalikApi.State.STOPPED
    }

    def "should start instance"() {
        given:
        def instances = api.listInstances().blockingFirst()

        and:
        def instance = instances[0] as Instance

        when:
        api.startInstance(instance.uuid).blockingFirst()

        and:
        TimeUnit.SECONDS.sleep(30)

        and:
        def instance2 = api.getInstance(instance.uuid).blockingFirst()

        then:
        instance2.state == TiktalikApi.State.RUNNING
    }

    def "should destroy instance"() {
        given:
        def instances = api.listInstances().blockingFirst()

        when:
        api.destroyInstance(instances[0].uuid).blockingFirst()

        and:
        TimeUnit.SECONDS.sleep(30)

        and:
        def instances2 = api.listInstances().blockingFirst()

        then:
        instances2.size() == 0
    }
}