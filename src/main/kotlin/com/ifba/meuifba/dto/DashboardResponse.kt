package com.ifba.meuifba.dto

data class DashboardResponse(
    val totalEventos: Int,
    val totalUsuarios: Int,
    val totalMarcacoes: Int,
    val eventosFuturos: Int,
    val eventosMaisPopulares: List<EventoPopularResponse>,
    val eventosPorCategoria: List<CategoriaStatResponse>,
    val categoriasPorMarcacoes: List<CategoriaStatResponse>,
    val marcacoesPorTurno: List<TurnoStatResponse>
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

data class TurnoStatResponse(
    val turno: String,
    val totalMarcacoes: Int
)