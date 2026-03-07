package com.ifba.meuifba.service

import com.ifba.meuifba.dto.CategoriaEventoResponse
import com.ifba.meuifba.repository.CategoriaEventoRepository
import org.springframework.stereotype.Service

@Service
class CategoriaService(
    private val categoriaEventoRepository: CategoriaEventoRepository
) {
    fun getCategorias(): List<CategoriaEventoResponse> =
        categoriaEventoRepository.findByAtivo(true).map {
            CategoriaEventoResponse(
                id = it.id, nome = it.nome,
                descricao = it.descricao, icone = it.icone, cor = it.cor
            )
        }
}