package com.ifba.meuifba.repository

import com.ifba.meuifba.model.Evento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventoRepository : JpaRepository<Evento, Long> {
    fun findByCategoriaId(categoriaId: Long): List<Evento>
    fun findByTituloContainingIgnoreCase(titulo: String): List<Evento>
    fun findByUsuarioCriadorId(usuarioId: Long): List<Evento>
}