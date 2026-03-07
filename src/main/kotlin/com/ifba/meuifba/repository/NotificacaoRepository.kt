package com.ifba.meuifba.repository

import com.ifba.meuifba.model.Notificacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificacaoRepository : JpaRepository<Notificacao, Long> {
    fun findByUsuarioId(usuarioId: Long): List<Notificacao>
    fun findByUsuarioIdAndVisualizada(usuarioId: Long, visualizada: Boolean): List<Notificacao>
}