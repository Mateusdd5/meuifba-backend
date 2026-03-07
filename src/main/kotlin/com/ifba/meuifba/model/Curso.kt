package com.ifba.meuifba.model

import jakarta.persistence.*

@Entity
@Table(name = "cursos")
data class Curso(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val nome: String = "",

    @Column
    val descricao: String? = null,

    @Column
    val turno: String? = null,

    @Column(name = "area_conhecimento_id")
    val areaConhecimentoId: Long? = null,

    @Column(nullable = false)
    val ativo: Boolean = true
)
