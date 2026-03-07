package com.ifba.meuifba.dto

data class UpdateUsuarioRequest(
    val nome: String = "",
    val fotoPerfil: String? = null,
    val matricula: String? = null,
    val cursoId: Long? = null
)