package com.micronaut

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class GreetingControllerTest {

    @Inject
    @Client("/")
    var client: RxHttpClient? = null

    @Test
    @Disabled
    fun testGreeting() {
        val request: HttpRequest<String> = HttpRequest.GET("/hello")
        val body = client!!.toBlocking().retrieve(request)
        assertNotNull(body)
        assertEquals("Hello from Micronaut!", body)
    }
}