package com.ifba.meuifba.repository

import com.ifba.meuifba.model.LogAtividade
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LogAtividadeRepository : JpaRepository<LogAtividade, Long> {
    fun findByUsuarioId(usuarioId: Long): List<LogAtividade>
}