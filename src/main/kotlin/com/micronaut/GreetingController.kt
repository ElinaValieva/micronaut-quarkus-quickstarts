package com.micronaut

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
class GreetingController {

    @Get(uris = ["/hello/{name}", "/greeting/{name}"])
    fun greeting(name: String): String {
        return "Hello from Micronaut $name"
    }

    @Get(uris = ["/hello", "/greeting"])
    fun greeting(): String {
        return "Hello from Micronaut!"
    }
}