package com.ifba.meuifba.model

import jakarta.persistence.*

@Entity
@Table(name = "preferencias_usuario")
data class PreferenciaUsuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "usuario_id", nullable = false)
    val usuarioId: Long = 0,

    @Column(name = "categoria_id", nullable = false)
    val categoriaId: Long = 0
)