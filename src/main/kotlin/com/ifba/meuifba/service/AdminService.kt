package com.ifba.meuifba.service

import com.ifba.meuifba.dto.CategoriaStatResponse
import com.ifba.meuifba.dto.DashboardResponse
import com.ifba.meuifba.dto.EventoPopularResponse
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

        // Conta marcações diretamente da tabela de marcações (mais confiável)
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

        return DashboardResponse(
            totalEventos = totalEventos,
            totalUsuarios = totalUsuarios,
            totalMarcacoes = totalMarcacoes,
            eventosFuturos = eventosFuturos,
            eventosMaisPopulares = eventosMaisPopulares,
            eventosPorCategoria = eventosPorCategoria
        )
    }
}