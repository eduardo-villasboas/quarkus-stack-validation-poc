package com.quarkus.app.server

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GreetingResourceTest {

    @Test
    fun testHelloEndpoint() {
        val greetingResource = GreetingResource()
        val helloResult = greetingResource.hello()
        assertThat(
            helloResult
        ).isEqualTo(
            "Hello from RESTEasy Reactive"
        )

    }

}