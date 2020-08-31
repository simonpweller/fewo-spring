package de.sweller.fewospring

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HelloWorldController {

    @GetMapping("/")
    @ResponseBody
    fun helloWorld() = "Hello, World!"
}
