package com.ifba.meuifba.dto

data class CursoResponse(
    val id: Long = 0,
    val nome: String = "",
    val descricao: String? = null,
    val turno: String? = null,
    val areaConhecimento: AreaConhecimentoResponse? = null,
    val ativo: Boolean = true
)
