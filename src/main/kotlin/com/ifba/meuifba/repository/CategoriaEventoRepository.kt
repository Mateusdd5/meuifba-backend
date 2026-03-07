package com.ifba.meuifba.repository

import com.ifba.meuifba.model.CategoriaEvento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoriaEventoRepository : JpaRepository<CategoriaEvento, Long> {
    fun findByAtivo(ativo: Boolean): List<CategoriaEvento>
}