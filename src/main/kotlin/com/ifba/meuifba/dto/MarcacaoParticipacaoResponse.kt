package com.ifba.meuifba.dto

data class MarcacaoParticipacaoResponse(
    val id: Long = 0,
    val usuarioId: Long = 0,
    val eventoId: Long = 0,
    val dataMarcacao: Long = 0,
    val status: String = ""
)