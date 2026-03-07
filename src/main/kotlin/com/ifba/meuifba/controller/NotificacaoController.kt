package com.ifba.meuifba.controller

import com.ifba.meuifba.service.NotificacaoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/notificacoes")
class NotificacaoController(
    private val notificacaoService: NotificacaoService
) {

    @GetMapping("/{usuarioId}")
    fun getNotificacoes(@PathVariable usuarioId: Long): ResponseEntity<Any> =
        ResponseEntity.ok(notificacaoService.getNotificacoes(usuarioId))

    @PutMapping("/{id}/visualizar")
    fun marcarVisualizada(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(notificacaoService.marcarVisualizada(id))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }
}