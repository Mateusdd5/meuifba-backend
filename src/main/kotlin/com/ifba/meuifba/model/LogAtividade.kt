package com.ifba.meuifba.model

import jakarta.persistence.*

@Entity
@Table(name = "logs_atividade")
data class LogAtividade(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "usuario_id", nullable = false)
    val usuarioId: Long = 0,

    @Column(nullable = false)
    val acao: String = "",

    @Column
    val descricao: String? = null,

    @Column(name = "data_acao")
    val dataAcao: Long = System.currentTimeMillis()
)