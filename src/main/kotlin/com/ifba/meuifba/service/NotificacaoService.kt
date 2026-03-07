package com.ifba.meuifba.service

import com.ifba.meuifba.dto.NotificacaoResponse
import com.ifba.meuifba.repository.NotificacaoRepository
import org.springframework.stereotype.Service

@Service
class NotificacaoService(
    private val notificacaoRepository: NotificacaoRepository
) {
    fun getNotificacoes(usuarioId: Long): List<NotificacaoResponse> =
        notificacaoRepository.findByUsuarioId(usuarioId).map { it.toResponse() }

    fun marcarVisualizada(id: Long): NotificacaoResponse {
        val notificacao = notificacaoRepository.findById(id)
            .orElseThrow { RuntimeException("Notificação não encontrada") }
        val updated = notificacao.copy(visualizada = true)
        return notificacaoRepository.save(updated).toResponse()
    }

    private fun com.ifba.meuifba.model.Notificacao.toResponse() = NotificacaoResponse(
        id = id, usuarioId = usuarioId, eventoId = eventoId,
        mensagem = mensagem, dataEnvio = dataEnvio,
        visualizada = visualizada, tipoNotificacao = tipoNotificacao
    )
}
