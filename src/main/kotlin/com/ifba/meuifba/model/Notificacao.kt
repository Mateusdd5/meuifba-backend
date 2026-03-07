package com.ifba.meuifba.model

import jakarta.persistence.*

@Entity
@Table(name = "notificacoes")
data class Notificacao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "usuario_id", nullable = false)
    val usuarioId: Long = 0,

    @Column(name = "evento_id", nullable = false)
    val eventoId: Long = 0,

    @Column(nullable = false, columnDefinition = "TEXT")
    val mensagem: String = "",

    @Column(name = "data_envio")
    val dataEnvio: Long = System.currentTimeMillis(),

    @Column(nullable = false)
    val visualizada: Boolean = false,

    @Column(name = "tipo_notificacao")
    val tipoNotificacao: String = "GERAL"
)