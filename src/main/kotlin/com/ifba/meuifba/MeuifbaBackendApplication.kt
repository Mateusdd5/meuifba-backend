package com.ifba.meuifba

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MeuifbaBackendApplication

fun main(args: Array<String>) {
    runApplication<MeuifbaBackendApplication>(*args)
}
