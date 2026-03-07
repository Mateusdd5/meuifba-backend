package com.ifba.meuifba.dto

data class EventoResponse(
    val id: Long = 0,
    val titulo: String = "",
    val descricao: String = "",
    val categoriaId: Long = 0,
    val categoriaNome: String = "",
    val dataEvento: Long = 0,
    val horarioInicio: String = "",
    val horarioFim: String = "",
    val local: String = "",
    val publicoAlvo: String = "",
    val cargaHoraria: Int = 0,
    val certificacao: Boolean = false,
    val requisitos: String? = null,
    val numeroVagas: Int = 0,
    val vagasDisponiveis: Int = 0,
    val statusInscricao: String = "",
    val usuarioCriadorId: Long = 0,
    val usuarioCriadorNome: String = "",
    val dataCriacao: Long = 0,
    val dataAtualizacao: Long = 0
)