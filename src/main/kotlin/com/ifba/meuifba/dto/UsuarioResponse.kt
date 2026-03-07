package com.ifba.meuifba.dto

data class UsuarioResponse(
    val id: Long = 0,
    val nome: String = "",
    val email: String = "",
    val tipoUsuario: String = "",
    val fotoPerfil: String? = null,
    val curso: CursoResponse? = null,
    val dataCadastro: Long = 0,
    val statusConta: String = "ATIVO",
    val ativo: Boolean = true
)