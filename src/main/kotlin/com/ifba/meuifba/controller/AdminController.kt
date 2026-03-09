package com.ifba.meuifba.controller

import com.ifba.meuifba.service.AdminService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class AdminController(
    private val adminService: AdminService
) {

    @GetMapping("/dashboard")
    fun getDashboard(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(adminService.getDashboard())
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }
}