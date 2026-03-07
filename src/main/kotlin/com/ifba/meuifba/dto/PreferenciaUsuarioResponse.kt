package com.ifba.meuifba.dto

data class PreferenciaUsuarioResponse(
    val id: Long = 0,
    val usuarioId: Long = 0,
    val categoriaId: Long = 0,
    val categoriaNome: String = ""
)
