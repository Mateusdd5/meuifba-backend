package com.ifba.meuifba

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun main() {
    val encoder = BCryptPasswordEncoder()
    val senha = "admin123"
    val hash = encoder.encode(senha)
    println("Senha: $senha")
    println("Hash:  $hash")
}