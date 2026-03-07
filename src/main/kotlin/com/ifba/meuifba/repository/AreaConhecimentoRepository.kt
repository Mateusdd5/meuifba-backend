package com.ifba.meuifba.repository

import com.ifba.meuifba.model.AreaConhecimento
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AreaConhecimentoRepository : JpaRepository<AreaConhecimento, Long> {
    fun findByAtivo(ativo: Boolean): List<AreaConhecimento>
}