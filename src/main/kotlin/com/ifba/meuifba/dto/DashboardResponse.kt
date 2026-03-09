package com.ifba.meuifba.dto

data class DashboardResponse(
    val totalEventos: Int,
    val totalUsuarios: Int,
    val totalMarcacoes: Int,
    val eventosFuturos: Int,
    val eventosMaisPopulares: List<EventoPopularResponse>,
    val eventosPorCategoria: List<CategoriaStatResponse>
)

data class EventoPopularResponse(
    val eventoId: Long,
    val titulo: String,
    val categoria: String,
    val totalMarcacoes: Int,
    val vagasDisponiveis: Int,
    val numeroVagas: Int
)

data class CategoriaStatResponse(
    val categoria: String,
    val total: Int
)