package com.ifba.meuifba.service

import com.ifba.meuifba.dto.AreaConhecimentoResponse
import com.ifba.meuifba.dto.CursoResponse
import com.ifba.meuifba.repository.AreaConhecimentoRepository
import com.ifba.meuifba.repository.CursoRepository
import org.springframework.stereotype.Service

@Service
class CursoService(
    private val cursoRepository: CursoRepository,
    private val areaConhecimentoRepository: AreaConhecimentoRepository
) {
    fun getCursos(): List<CursoResponse> =
        cursoRepository.findByAtivo(true).map { curso ->
            CursoResponse(
                id = curso.id,
                nome = curso.nome,
                descricao = curso.descricao,
                turno = curso.turno,
                areaConhecimento = curso.areaConhecimentoId?.let { areaId ->
                    areaConhecimentoRepository.findById(areaId).orElse(null)?.let { area ->
                        AreaConhecimentoResponse(
                            id = area.id,
                            nome = area.nome,
                            descricao = area.descricao
                        )
                    }
                },
                ativo = curso.ativo
            )
        }
}