package com.micronaut

import io.micronaut.function.FunctionBean
import java.util.function.Supplier

@FunctionBean("hello-micronaut")
class HelloKotlinFunction : Supplier<String> {

    override fun get(): String {
        return "Hello world!"
    }
}
