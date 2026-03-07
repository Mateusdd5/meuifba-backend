package com.ifba.meuifba.model

import jakarta.persistence.*

@Entity
@Table(name = "eventos")
data class Evento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val titulo: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val descricao: String = "",

    @Column(name = "categoria_id", nullable = false)
    val categoriaId: Long = 0,

    @Column(name = "data_evento", nullable = false)
    val dataEvento: Long = 0,

    @Column(name = "horario_inicio")
    val horarioInicio: String = "",

    @Column(name = "horario_fim")
    val horarioFim: String = "",

    @Column(nullable = false)
    val local: String = "",

    @Column(name = "publico_alvo")
    val publicoAlvo: String = "",

    @Column(name = "carga_horaria")
    val cargaHoraria: Int = 0,

    @Column(nullable = false)
    val certificacao: Boolean = false,

    @Column(columnDefinition = "TEXT")
    val requisitos: String? = null,

    @Column(name = "numero_vagas")
    val numeroVagas: Int = 0,

    @Column(name = "vagas_disponiveis")
    val vagasDisponiveis: Int = 0,

    @Column(name = "status_inscricao")
    val statusInscricao: String = "ABERTO",

    @Column(name = "usuario_criador_id", nullable = false)
    val usuarioCriadorId: Long = 0,

    @Column(name = "data_criacao")
    val dataCriacao: Long = System.currentTimeMillis(),

    @Column(name = "data_atualizacao")
    val dataAtualizacao: Long = System.currentTimeMillis()
)