package com.ifba.meuifba.dto

data class UpdateEventoRequest(
    val titulo: String = "",
    val descricao: String = "",
    val categoriaId: Long = 0,
    val dataEvento: Long = 0,
    val horarioInicio: String = "",
    val horarioFim: String = "",
    val local: String = "",
    val publicoAlvo: String = "",
    val cargaHoraria: Int = 0,
    val certificacao: Boolean = false,
    val requisitos: String? = null,
    val numeroVagas: Int = 0,
    val statusInscricao: String = ""
)