package com.ifba.meuifba.model

import jakarta.persistence.*

@Entity
@Table(name = "categorias_evento")
data class CategoriaEvento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val nome: String = "",

    @Column(columnDefinition = "TEXT")
    val descricao: String? = null,

    @Column
    val icone: String? = null,

    @Column
    val cor: String? = null,

    @Column(nullable = false)
    val ativo: Boolean = true
)