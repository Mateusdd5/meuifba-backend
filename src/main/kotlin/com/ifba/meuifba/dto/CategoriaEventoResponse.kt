package com.ifba.meuifba.dto

data class CategoriaEventoResponse(
    val id: Long = 0,
    val nome: String = "",
    val descricao: String? = null,
    val icone: String? = null,
    val cor: String? = null
)