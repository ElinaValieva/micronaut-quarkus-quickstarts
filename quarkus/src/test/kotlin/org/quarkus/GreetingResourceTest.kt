package org.quarkus

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured
import org.hamcrest.core.Is.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
open class GreetingResourceTest {

    @Test
    fun testHelloEndpoint() {
        RestAssured.given()
                .`when`().get("/hello")
                .then()
                .statusCode(200)
                .body(`is`("Hello from Quarkus!"))
    }
}