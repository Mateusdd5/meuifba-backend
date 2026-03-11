package com.ifba.meuifba.service

import com.ifba.meuifba.dto.*
import com.ifba.meuifba.repository.*
import org.springframework.stereotype.Service

@Service
class AdminService(
    private val eventoRepository: EventoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val marcacaoRepository: MarcacaoParticipacaoRepository,
    private val estatisticaRepository: EstatisticaEventoRepository,
    private val categoriaEventoRepository: CategoriaEventoRepository
) {

    fun getDashboard(): DashboardResponse {
        val agora = System.currentTimeMillis()

        val totalEventos = eventoRepository.count().toInt()
        val totalUsuarios = usuarioRepository.count().toInt()
        val totalMarcacoes = marcacaoRepository.count().toInt()
        val eventosFuturos = eventoRepository.findAll()
            .count { it.dataEvento > agora }

        // Eventos mais populares (top 5 por marcações diretas)
        val eventosMaisPopulares = eventoRepository.findAll()
            .map { evento ->
                val totalMarcacoesEvento = marcacaoRepository.findByEventoId(evento.id).size
                Pair(evento, totalMarcacoesEvento)
            }
            .filter { it.second > 0 }
            .sortedByDescending { it.second }
            .take(5)
            .map { (evento, totalMarcacoesEvento) ->
                val categoria = categoriaEventoRepository.findById(evento.categoriaId).orElse(null)
                EventoPopularResponse(
                    eventoId = evento.id,
                    titulo = evento.titulo,
                    categoria = categoria?.nome ?: "Sem categoria",
                    totalMarcacoes = totalMarcacoesEvento,
                    vagasDisponiveis = evento.vagasDisponiveis,
                    numeroVagas = evento.numeroVagas
                )
            }

        // Quantidade de eventos por categoria
        val eventosPorCategoria = eventoRepository.findAll()
            .groupBy { it.categoriaId }
            .map { (categoriaId, eventos) ->
                val categoria = categoriaEventoRepository.findById(categoriaId).orElse(null)
                CategoriaStatResponse(
                    categoria = categoria?.nome ?: "Sem categoria",
                    total = eventos.size
                )
            }
            .sortedByDescending { it.total }

        // Marcações acumuladas por categoria
        val categoriasPorMarcacoes = eventoRepository.findAll()
            .groupBy { it.categoriaId }
            .map { (categoriaId, eventos) ->
                val categoria = categoriaEventoRepository.findById(categoriaId).orElse(null)
                val totalMarcacoesCategoria = eventos.sumOf { evento ->
                    marcacaoRepository.findByEventoId(evento.id).size
                }
                CategoriaStatResponse(
                    categoria = categoria?.nome ?: "Sem categoria",
                    total = totalMarcacoesCategoria
                )
            }
            .filter { it.total > 0 }
            .sortedByDescending { it.total }

        // Marcações ponderadas por turno
        val marcacoesPorTurno = mutableMapOf(
            "Matutino" to 0,
            "Vespertino" to 0,
            "Noturno" to 0
        )

        eventoRepository.findAll().forEach { evento ->
            val marcacoesEvento = marcacaoRepository.findByEventoId(evento.id).size
            if (marcacoesEvento == 0) return@forEach

            val horaInicio = parseHora(evento.horarioInicio)
            val horaFim = parseHora(evento.horarioFim)

            if (horaInicio != null && horaFim != null) {
                if (horaInicio < 12 * 60) {
                    marcacoesPorTurno["Matutino"] = marcacoesPorTurno["Matutino"]!! + marcacoesEvento
                }
                if (horaInicio < 18 * 60 && horaFim > 12 * 60) {
                    marcacoesPorTurno["Vespertino"] = marcacoesPorTurno["Vespertino"]!! + marcacoesEvento
                }
                if (horaInicio >= 18 * 60) {
                    marcacoesPorTurno["Noturno"] = marcacoesPorTurno["Noturno"]!! + marcacoesEvento
                }
            }
        }

        val turnoStats = marcacoesPorTurno
            .map { (turno, total) -> TurnoStatResponse(turno = turno, totalMarcacoes = total) }
            .sortedByDescending { it.totalMarcacoes }

        return DashboardResponse(
            totalEventos = totalEventos,
            totalUsuarios = totalUsuarios,
            totalMarcacoes = totalMarcacoes,
            eventosFuturos = eventosFuturos,
            eventosMaisPopulares = eventosMaisPopulares,
            eventosPorCategoria = eventosPorCategoria,
            categoriasPorMarcacoes = categoriasPorMarcacoes,
            marcacoesPorTurno = turnoStats
        )
    }

    private fun parseHora(horario: String?): Int? {
        if (horario.isNullOrBlank()) return null
        return try {
            val partes = horario.trim().split(":")
            partes[0].toInt() * 60 + partes[1].toInt()
        } catch (e: Exception) {
            null
        }
    }
}