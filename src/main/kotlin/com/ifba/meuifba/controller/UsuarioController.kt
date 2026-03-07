package com.ifba.meuifba.controller

import com.ifba.meuifba.dto.UpdateSenhaRequest
import com.ifba.meuifba.dto.UpdateUsuarioRequest
import com.ifba.meuifba.service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuarios")
class UsuarioController(
    private val usuarioService: UsuarioService
) {

    @GetMapping("/{id}")
    fun getUsuarioById(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(usuarioService.getUsuarioById(id))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    @PutMapping("/{id}")
    fun updateUsuario(
        @PathVariable id: Long,
        @RequestBody request: UpdateUsuarioRequest
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(usuarioService.updateUsuario(id, request))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    @PutMapping("/{id}/senha")
    fun updateSenha(
        @PathVariable id: Long,
        @RequestBody request: UpdateSenhaRequest
    ): ResponseEntity<Any> {
        return try {
            usuarioService.updateSenha(id, request)
            ResponseEntity.ok(mapOf("mensagem" to "Senha atualizada com sucesso"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    @GetMapping("/{id}/preferencias")
    fun getPreferencias(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(usuarioService.getPreferencias(id))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    @PostMapping("/{id}/preferencias")
    fun updatePreferencias(
        @PathVariable id: Long,
        @RequestBody categoriaIds: List<Long>
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(usuarioService.updatePreferencias(id, categoriaIds))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }
}
