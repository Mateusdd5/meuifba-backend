package com.ifba.meuifba.repository

import com.ifba.meuifba.model.MidiaEvento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MidiaEventoRepository : JpaRepository<MidiaEvento, Long> {
    fun findByEventoId(eventoId: Long): List<MidiaEvento>
}