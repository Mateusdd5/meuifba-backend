package com.ifba.meuifba.dto

data class RegisterRequest(
    val nome: String = "",
    val email: String = "",
    val senha: String = "",
    val tipoUsuario: String = "USUARIO",
    val matricula: String? = null,
    val cursoId: Long? = null
)