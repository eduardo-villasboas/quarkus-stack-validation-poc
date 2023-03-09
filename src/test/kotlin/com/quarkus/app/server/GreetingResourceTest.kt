package com.quarkus.app.server

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GreetingResourceTest {

    @Test
    fun testHelloEndpoint() {

        val greetingResource = GreetingResource()

        val helloResult = greetingResource.hello()
        //This thread sleep is here just to be possible to check if unit tests are running
        Thread.sleep(2000)
        assertThat(
            helloResult
        ).isEqualTo(
            "Hello from RESTEasy Reactive"
        )
    }

}