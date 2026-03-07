package com.ifba.meuifba.repository

import com.ifba.meuifba.model.Curso
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CursoRepository : JpaRepository<Curso, Long> {
    fun findByAtivo(ativo: Boolean): List<Curso>
}