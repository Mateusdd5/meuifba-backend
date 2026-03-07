package com.ifba.meuifba.service

import com.ifba.meuifba.dto.*
import com.ifba.meuifba.model.Evento
import com.ifba.meuifba.model.EstatisticaEvento
import com.ifba.meuifba.model.MarcacaoParticipacao
import com.ifba.meuifba.repository.*
import org.springframework.stereotype.Service

@Service
class EventoService(
    private val eventoRepository: EventoRepository,
    private val categoriaEventoRepository: CategoriaEventoRepository,
    private val usuarioRepository: UsuarioRepository,
    private val marcacaoRepository: MarcacaoParticipacaoRepository,
    private val estatisticaRepository: EstatisticaEventoRepository,
    private val midiaRepository: MidiaEventoRepository
) {

    fun getEventos(): List<EventoResponse> =
        eventoRepository.findAll().map { it.toResponse() }

    fun getEventoById(id: Long): EventoResponse {
        val evento = eventoRepository.findById(id)
            .orElseThrow { RuntimeException("Evento não encontrado") }
        return evento.toResponse()
    }

    fun getEventosByCategoria(categoriaId: Long): List<EventoResponse> =
        eventoRepository.findByCategoriaId(categoriaId).map { it.toResponse() }

    fun searchEventos(query: String): List<EventoResponse> =
        eventoRepository.findByTituloContainingIgnoreCase(query).map { it.toResponse() }

    fun createEvento(request: CreateEventoRequest): EventoResponse {
        val evento = Evento(
            titulo = request.titulo,
            descricao = request.descricao,
            categoriaId = request.categoriaId,
            dataEvento = request.dataEvento,
            horarioInicio = request.horarioInicio,
            horarioFim = request.horarioFim,
            local = request.local,
            publicoAlvo = request.publicoAlvo,
            cargaHoraria = request.cargaHoraria,
            certificacao = request.certificacao,
            requisitos = request.requisitos,
            numeroVagas = request.numeroVagas,
            vagasDisponiveis = request.numeroVagas,
            usuarioCriadorId = request.usuarioCriadorId
        )
        val saved = eventoRepository.save(evento)
        estatisticaRepository.save(EstatisticaEvento(eventoId = saved.id))
        return saved.toResponse()
    }

    fun updateEvento(id: Long, request: UpdateEventoRequest): EventoResponse {
        val evento = eventoRepository.findById(id)
            .orElseThrow { RuntimeException("Evento não encontrado") }
        val updated = evento.copy(
            titulo = request.titulo,
            descricao = request.descricao,
            categoriaId = request.categoriaId,
            dataEvento = request.dataEvento,
            horarioInicio = request.horarioInicio,
            horarioFim = request.horarioFim,
            local = request.local,
            publicoAlvo = request.publicoAlvo,
            cargaHoraria = request.cargaHoraria,
            certificacao = request.certificacao,
            requisitos = request.requisitos,
            numeroVagas = request.numeroVagas,
            statusInscricao = request.statusInscricao,
            dataAtualizacao = System.currentTimeMillis()
        )
        return eventoRepository.save(updated).toResponse()
    }

    fun deleteEvento(id: Long) {
        eventoRepository.deleteById(id)
    }

    fun marcarParticipacao(request: MarcarParticipacaoRequest): MarcacaoParticipacaoResponse {
        if (marcacaoRepository.existsByUsuarioIdAndEventoId(request.usuarioId, request.eventoId)) {
            throw RuntimeException("Usuário já marcou presença neste evento")
        }
        val marcacao = MarcacaoParticipacao(
            usuarioId = request.usuarioId,
            eventoId = request.eventoId
        )
        val saved = marcacaoRepository.save(marcacao)
        return MarcacaoParticipacaoResponse(
            id = saved.id,
            usuarioId = saved.usuarioId,
            eventoId = saved.eventoId,
            dataMarcacao = saved.dataMarcacao,
            status = saved.status
        )
    }

    fun desmarcarParticipacao(usuarioId: Long, eventoId: Long) {
        marcacaoRepository.deleteByUsuarioIdAndEventoId(usuarioId, eventoId)
    }

    fun getEventosMarcados(usuarioId: Long): List<EventoResponse> {
        val marcacoes = marcacaoRepository.findByUsuarioId(usuarioId)
        return marcacoes.mapNotNull { marcacao ->
            eventoRepository.findById(marcacao.eventoId).orElse(null)?.toResponse()
        }
    }

    fun getEstatisticas(eventoId: Long): EstatisticaEventoResponse {
        val estatistica = estatisticaRepository.findByEventoId(eventoId)
            ?: throw RuntimeException("Estatísticas não encontradas")
        return EstatisticaEventoResponse(
            id = estatistica.id,
            eventoId = estatistica.eventoId,
            totalVisualizacoes = estatistica.totalVisualizacoes,
            totalMarcacoes = estatistica.totalMarcacoes,
            totalConfirmados = estatistica.totalConfirmados
        )
    }

    fun getMidias(eventoId: Long) = midiaRepository.findByEventoId(eventoId)

    private fun Evento.toResponse(): EventoResponse {
        val categoria = categoriaEventoRepository.findById(categoriaId).orElse(null)
        val criador = usuarioRepository.findById(usuarioCriadorId).orElse(null)
        return EventoResponse(
            id = id, titulo = titulo, descricao = descricao,
            categoriaId = categoriaId, categoriaNome = categoria?.nome ?: "",
            dataEvento = dataEvento, horarioInicio = horarioInicio,
            horarioFim = horarioFim, local = local, publicoAlvo = publicoAlvo,
            cargaHoraria = cargaHoraria, certificacao = certificacao,
            requisitos = requisitos, numeroVagas = numeroVagas,
            vagasDisponiveis = vagasDisponiveis, statusInscricao = statusInscricao,
            usuarioCriadorId = usuarioCriadorId, usuarioCriadorNome = criador?.nome ?: "",
            dataCriacao = dataCriacao, dataAtualizacao = dataAtualizacao
        )
    }
}