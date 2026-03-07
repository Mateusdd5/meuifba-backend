package com.ifba.meuifba.model

import jakarta.persistence.*

@Entity
@Table(name = "areas_conhecimento")
data class AreaConhecimento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val nome: String = "",

    @Column
    val descricao: String? = null,

    @Column(nullable = false)
    val ativo: Boolean = true
)