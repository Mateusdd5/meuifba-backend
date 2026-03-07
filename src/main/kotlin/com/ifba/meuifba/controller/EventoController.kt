package com.ifba.meuifba.controller

import com.ifba.meuifba.dto.CreateEventoRequest
import com.ifba.meuifba.dto.MarcarParticipacaoRequest
import com.ifba.meuifba.dto.UpdateEventoRequest
import com.ifba.meuifba.service.EventoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/eventos")
class EventoController(
    private val eventoService: EventoService
) {

    @GetMapping
    fun getEventos(): ResponseEntity<Any> =
        ResponseEntity.ok(eventoService.getEventos())

    @GetMapping("/{id}")
    fun getEventoById(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(eventoService.getEventoById(id))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    @GetMapping("/categoria/{categoriaId}")
    fun getEventosByCategoria(@PathVariable categoriaId: Long): ResponseEntity<Any> =
        ResponseEntity.ok(eventoService.getEventosByCategoria(categoriaId))

    @GetMapping("/search")
    fun searchEventos(@RequestParam q: String): ResponseEntity<Any> =
        ResponseEntity.ok(eventoService.searchEventos(q))

    @PostMapping
    fun createEvento(@RequestBody request: CreateEventoRequest): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(eventoService.createEvento(request))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    @PutMapping("/{id}")
    fun updateEvento(
        @PathVariable id: Long,
        @RequestBody request: UpdateEventoRequest
    ): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(eventoService.updateEvento(id, request))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    @DeleteMapping("/{id}")
    fun deleteEvento(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            eventoService.deleteEvento(id)
            ResponseEntity.ok(mapOf("mensagem" to "Evento deletado com sucesso"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    @PostMapping("/marcar")
    fun marcarParticipacao(@RequestBody request: MarcarParticipacaoRequest): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(eventoService.marcarParticipacao(request))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    @DeleteMapping("/marcar/{usuarioId}/{eventoId}")
    fun desmarcarParticipacao(
        @PathVariable usuarioId: Long,
        @PathVariable eventoId: Long
    ): ResponseEntity<Any> {
        return try {
            eventoService.desmarcarParticipacao(usuarioId, eventoId)
            ResponseEntity.ok(mapOf("mensagem" to "Participação desmarcada com sucesso"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    @GetMapping("/marcados/{usuarioId}")
    fun getEventosMarcados(@PathVariable usuarioId: Long): ResponseEntity<Any> =
        ResponseEntity.ok(eventoService.getEventosMarcados(usuarioId))

    @GetMapping("/{id}/estatisticas")
    fun getEstatisticas(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(eventoService.getEstatisticas(id))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    @GetMapping("/{id}/midias")
    fun getMidias(@PathVariable id: Long): ResponseEntity<Any> =
        ResponseEntity.ok(eventoService.getMidias(id))
}