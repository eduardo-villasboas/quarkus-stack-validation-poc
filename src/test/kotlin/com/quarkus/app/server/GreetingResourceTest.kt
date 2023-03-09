package com.quarkus.app.server

import org.junit.jupiter.api.Test

class GreetingResourceTest {

    @Test
    fun testHelloEndpoint() {
        Thread.sleep(3000)
        val greetingResource = GreetingResource()

        greetingResource.hello()

        //TODO("FAIL UNIT")
        /*
        given()
          .`when`().get("/hello")
          .then()
             .statusCode(200)
             .body(`is`("Hello from RESTEasy Reactive"))

         */
    }

}