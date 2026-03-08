package com.ifba.meuifba.service

import com.ifba.meuifba.dto.*
import com.ifba.meuifba.model.Evento
import com.ifba.meuifba.model.EstatisticaEvento
import com.ifba.meuifba.model.MarcacaoParticipacao
import com.ifba.meuifba.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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
        eventoRepository.findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(query, query)
            .map { it.toResponse() }

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

    fun updateEvento(id: Long, request: UpdateEventoRequest, requesterId: Long) {
        val evento = eventoRepository.findById(id)
            .orElseThrow { RuntimeException("Evento não encontrado") }

        val requester = usuarioRepository.findById(requesterId)
            .orElseThrow { RuntimeException("Usuário não encontrado") }

        val podeEditar = evento.usuarioCriadorId == requesterId
                || requester.tipoUsuario == "ADMIN"

        if (!podeEditar) throw RuntimeException("Sem permissão para editar este evento")

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
        eventoRepository.save(updated)
    }

    fun deleteEvento(id: Long, requesterId: Long) {
        val evento = eventoRepository.findById(id)
            .orElseThrow { RuntimeException("Evento não encontrado") }

        val requester = usuarioRepository.findById(requesterId)
            .orElseThrow { RuntimeException("Usuário não encontrado") }

        val podeDeletar = evento.usuarioCriadorId == requesterId
                || requester.tipoUsuario == "ADMIN"

        if (!podeDeletar) throw RuntimeException("Sem permissão para deletar este evento")

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

        // Decrementar vagas disponíveis
        val evento = eventoRepository.findById(request.eventoId).orElse(null)
        if (evento != null && evento.vagasDisponiveis > 0) {
            eventoRepository.save(
                evento.copy(vagasDisponiveis = evento.vagasDisponiveis - 1)
            )
        }

        // Atualizar estatísticas
        val estatistica = estatisticaRepository.findByEventoId(request.eventoId)
        if (estatistica != null) {
            estatisticaRepository.save(
                estatistica.copy(totalMarcacoes = estatistica.totalMarcacoes + 1)
            )
        }

        return MarcacaoParticipacaoResponse(
            id = saved.id,
            usuarioId = saved.usuarioId,
            eventoId = saved.eventoId,
            dataMarcacao = saved.dataMarcacao,
            status = saved.status
        )
    }

    @Transactional
    fun desmarcarParticipacao(usuarioId: Long, eventoId: Long) {
        marcacaoRepository.deleteByUsuarioIdAndEventoId(usuarioId, eventoId)

        // Incrementar vagas disponíveis de volta
        val evento = eventoRepository.findById(eventoId).orElse(null)
        if (evento != null && evento.numeroVagas > 0) {
            eventoRepository.save(
                evento.copy(vagasDisponiveis = minOf(evento.vagasDisponiveis + 1, evento.numeroVagas))
            )
        }

        // Atualizar estatísticas
        val estatistica = estatisticaRepository.findByEventoId(eventoId)
        if (estatistica != null && estatistica.totalMarcacoes > 0) {
            estatisticaRepository.save(
                estatistica.copy(totalMarcacoes = estatistica.totalMarcacoes - 1)
            )
        }
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
            id = id,
            titulo = titulo,
            descricao = descricao,
            categoria = CategoriaEventoResponse(
                id = categoria?.id ?: 0,
                nome = categoria?.nome ?: "",
                descricao = categoria?.descricao
            ),
            dataEvento = dataEvento,
            horarioInicio = horarioInicio,
            horarioFim = horarioFim,
            local = local,
            publicoAlvo = publicoAlvo,
            cargaHoraria = cargaHoraria,
            certificacao = certificacao,
            requisitos = requisitos,
            numeroVagas = numeroVagas,
            vagasDisponiveis = vagasDisponiveis,
            statusInscricao = statusInscricao,
            usuarioCriador = UsuarioCriadorResponse(
                id = criador?.id ?: 0,
                nome = criador?.nome ?: "",
                fotoPerfil = null
            ),
            dataCriacao = dataCriacao,
            dataAtualizacao = dataAtualizacao
        )
    }
}