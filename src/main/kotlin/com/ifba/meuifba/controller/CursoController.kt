package com.ifba.meuifba.controller

import com.ifba.meuifba.service.CursoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/cursos")
class CursoController(
    private val cursoService: CursoService
) {

    @GetMapping
    fun getCursos(): ResponseEntity<Any> =
        ResponseEntity.ok(cursoService.getCursos())
}