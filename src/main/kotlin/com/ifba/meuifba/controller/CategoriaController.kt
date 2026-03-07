package com.ifba.meuifba.controller

import com.ifba.meuifba.service.CategoriaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categorias")
class CategoriaController(
    private val categoriaService: CategoriaService
) {

    @GetMapping
    fun getCategorias(): ResponseEntity<Any> =
        ResponseEntity.ok(categoriaService.getCategorias())
}