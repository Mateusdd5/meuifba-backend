package com.ifba.meuifba.dto

data class UpdateSenhaRequest(
    val senhaAtual: String = "",
    val novaSenha: String = ""
)