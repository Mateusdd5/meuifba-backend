package com.ifba.meuifba.model

import jakarta.persistence.*

@Entity
@Table(name = "marcacoes_participacao")
data class MarcacaoParticipacao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "usuario_id", nullable = false)
    val usuarioId: Long = 0,

    @Column(name = "evento_id", nullable = false)
    val eventoId: Long = 0,

    @Column(name = "data_marcacao")
    val dataMarcacao: Long = System.currentTimeMillis(),

    @Column
    val status: String = "CONFIRMADO"
)