package com.ifba.meuifba.model

import jakarta.persistence.*

@Entity
@Table(name = "estatisticas_evento")
data class EstatisticaEvento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "evento_id", nullable = false, unique = true)
    val eventoId: Long = 0,

    @Column(name = "total_visualizacoes")
    val totalVisualizacoes: Int = 0,

    @Column(name = "total_marcacoes")
    val totalMarcacoes: Int = 0,

    @Column(name = "total_confirmados")
    val totalConfirmados: Int = 0
)