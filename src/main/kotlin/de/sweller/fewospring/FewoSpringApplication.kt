package de.sweller.fewospring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FewoSpringApplication

fun main(args: Array<String>) {
    runApplication<FewoSpringApplication>(*args)
}
