package com.ifba.meuifba.dto

data class EstatisticaEventoResponse(
    val id: Long = 0,
    val eventoId: Long = 0,
    val totalVisualizacoes: Int = 0,
    val totalMarcacoes: Int = 0,
    val totalConfirmados: Int = 0
)