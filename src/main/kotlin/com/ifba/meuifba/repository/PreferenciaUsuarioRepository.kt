package com.ifba.meuifba.repository

import com.ifba.meuifba.model.PreferenciaUsuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PreferenciaUsuarioRepository : JpaRepository<PreferenciaUsuario, Long> {
    fun findByUsuarioId(usuarioId: Long): List<PreferenciaUsuario>
    fun deleteByUsuarioId(usuarioId: Long)
}