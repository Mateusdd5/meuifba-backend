package com.ifba.meuifba.repository

import com.ifba.meuifba.model.MarcacaoParticipacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MarcacaoParticipacaoRepository : JpaRepository<MarcacaoParticipacao, Long> {
    fun findByUsuarioId(usuarioId: Long): List<MarcacaoParticipacao>
    fun findByEventoId(eventoId: Long): List<MarcacaoParticipacao>
    fun existsByUsuarioIdAndEventoId(usuarioId: Long, eventoId: Long): Boolean
    fun deleteByUsuarioIdAndEventoId(usuarioId: Long, eventoId: Long)
}