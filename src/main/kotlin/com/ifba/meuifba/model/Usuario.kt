package com.ifba.meuifba.model

import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val nome: String = "",

    @Column(nullable = false, unique = true)
    val email: String = "",

    @Column(nullable = false)
    var senha: String = "",

    @Column(name = "tipo_usuario", nullable = false)
    val tipoUsuario: String = "ALUNO",

    @Column(name = "foto_perfil")
    val fotoPerfil: String? = null,

    @Column(name = "matricula")
    val matricula: String? = null,

    @Column(name = "curso_id")
    val cursoId: Long? = null,

    @Column(name = "data_cadastro")
    val dataCadastro: Long = System.currentTimeMillis(),

    @Column(name = "ativo")
    val ativo: Boolean = true
)