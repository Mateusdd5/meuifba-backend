package com.ifba.meuifba.dto

data class LoginResponse(
    val token: String = "",
    val usuario: UsuarioResponse = UsuarioResponse()
)
