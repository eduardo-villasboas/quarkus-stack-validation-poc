package com.quarkus.app.server

import io.quarkus.test.junit.QuarkusIntegrationTest
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class GreetingResourceComponentTest {

    @Test
    fun testHelloEndpointOnComponentContext() {

        given()
          .`when`().get("/hello")
          .then()
             .statusCode(200)
             .body(`is`("Hello from RESTEasy Reactive"))
    }

}