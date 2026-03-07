package com.ifba.meuifba.repository

import com.ifba.meuifba.model.EstatisticaEvento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EstatisticaEventoRepository : JpaRepository<EstatisticaEvento, Long> {
    fun findByEventoId(eventoId: Long): EstatisticaEvento?
}