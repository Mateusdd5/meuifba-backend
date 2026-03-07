package com.ifba.meuifba.model

import jakarta.persistence.*

@Entity
@Table(name = "midias_evento")
data class MidiaEvento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "evento_id", nullable = false)
    val eventoId: Long = 0,

    @Column(nullable = false)
    val url: String = "",

    @Column(name = "tipo_midia")
    val tipoMidia: String = "IMAGEM",

    @Column
    val descricao: String? = null,

    @Column(name = "is_principal")
    val isPrincipal: Boolean = false
)