package com.ifba.meuifba.dto

data class EventoResponse(
    val id: Long = 0,
    val titulo: String = "",
    val descricao: String = "",
    val categoria: CategoriaEventoResponse = CategoriaEventoResponse(),
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
    val usuarioCriador: UsuarioCriadorResponse = UsuarioCriadorResponse(),
    val dataCriacao: Long = 0,
    val dataAtualizacao: Long = 0
)

data class UsuarioCriadorResponse(
    val id: Long = 0,
    val nome: String = "",
    val fotoPerfil: String? = null
)