package com.ifba.meuifba.service

import com.ifba.meuifba.dto.MarcacaoParticipacaoResponse
import com.ifba.meuifba.repository.MarcacaoParticipacaoRepository
import org.springframework.stereotype.Service

@Service
class ParticipacaoService(
    private val marcacaoRepository: MarcacaoParticipacaoRepository
) {
    fun getParticipacoesByUsuario(usuarioId: Long): List<MarcacaoParticipacaoResponse> =
        marcacaoRepository.findByUsuarioId(usuarioId).map {
            MarcacaoParticipacaoResponse(
                id = it.id,
                usuarioId = it.usuarioId,
                eventoId = it.eventoId,
                dataMarcacao = it.dataMarcacao,
                status = it.status
            )
        }

    fun getParticipacoesByEvento(eventoId: Long): List<MarcacaoParticipacaoResponse> =
        marcacaoRepository.findByEventoId(eventoId).map {
            MarcacaoParticipacaoResponse(
                id = it.id,
                usuarioId = it.usuarioId,
                eventoId = it.eventoId,
                dataMarcacao = it.dataMarcacao,
                status = it.status
            )
        }
}