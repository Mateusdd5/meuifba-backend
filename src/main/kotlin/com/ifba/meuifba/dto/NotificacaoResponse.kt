package com.ifba.meuifba.dto

data class NotificacaoResponse(
    val id: Long = 0,
    val usuarioId: Long = 0,
    val eventoId: Long = 0,
    val mensagem: String = "",
    val dataEnvio: Long = 0,
    val visualizada: Boolean = false,
    val tipoNotificacao: String = ""
)