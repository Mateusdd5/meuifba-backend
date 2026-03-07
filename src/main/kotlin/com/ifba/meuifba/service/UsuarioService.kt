package com.ifba.meuifba.service

import com.ifba.meuifba.dto.*
import com.ifba.meuifba.model.Usuario
import com.ifba.meuifba.model.PreferenciaUsuario
import com.ifba.meuifba.repository.UsuarioRepository
import com.ifba.meuifba.repository.PreferenciaUsuarioRepository
import com.ifba.meuifba.repository.CursoRepository
import com.ifba.meuifba.repository.AreaConhecimentoRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private val preferenciaUsuarioRepository: PreferenciaUsuarioRepository,
    private val cursoRepository: CursoRepository,
    private val areaConhecimentoRepository: AreaConhecimentoRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun getUsuarioById(id: Long): UsuarioResponse {
        val usuario = usuarioRepository.findById(id)
            .orElseThrow { RuntimeException("Usuário não encontrado") }
        return usuario.toResponse()
    }

    fun updateUsuario(id: Long, request: UpdateUsuarioRequest): UsuarioResponse {
        val usuario = usuarioRepository.findById(id)
            .orElseThrow { RuntimeException("Usuário não encontrado") }
        val updated = usuario.copy(
            nome = request.nome,
            fotoPerfil = request.fotoPerfil,
            matricula = request.matricula,
            cursoId = request.cursoId
        )
        return usuarioRepository.save(updated).toResponse()
    }

    fun updateSenha(id: Long, request: UpdateSenhaRequest) {
        val usuario = usuarioRepository.findById(id)
            .orElseThrow { RuntimeException("Usuário não encontrado") }
        if (!passwordEncoder.matches(request.senhaAtual, usuario.senha)) {
            throw RuntimeException("Senha atual incorreta")
        }
        val updated = usuario.copy(senha = passwordEncoder.encode(request.novaSenha))
        usuarioRepository.save(updated)
    }

    fun getPreferencias(usuarioId: Long): List<PreferenciaUsuarioResponse> {
        return preferenciaUsuarioRepository.findByUsuarioId(usuarioId)
            .map { PreferenciaUsuarioResponse(it.id, it.usuarioId, it.categoriaId) }
    }

    fun updatePreferencias(usuarioId: Long, categoriaIds: List<Long>): List<PreferenciaUsuarioResponse> {
        preferenciaUsuarioRepository.deleteByUsuarioId(usuarioId)
        val preferencias = categoriaIds.map {
            PreferenciaUsuario(usuarioId = usuarioId, categoriaId = it)
        }
        return preferenciaUsuarioRepository.saveAll(preferencias)
            .map { PreferenciaUsuarioResponse(it.id, it.usuarioId, it.categoriaId) }
    }

    private fun Usuario.toResponse(): UsuarioResponse {
        val curso = cursoId?.let { id ->
            cursoRepository.findById(id).orElse(null)?.let { curso ->
                CursoResponse(
                    id = curso.id,
                    nome = curso.nome,
                    descricao = curso.descricao,
                    turno = curso.turno,
                    areaConhecimento = curso.areaConhecimentoId?.let { areaId ->
                        areaConhecimentoRepository.findById(areaId).orElse(null)?.let { area ->
                            AreaConhecimentoResponse(
                                id = area.id,
                                nome = area.nome,
                                descricao = area.descricao
                            )
                        }
                    }
                )
            }
        }
        return UsuarioResponse(
            id = id,
            nome = nome,
            email = email,
            tipoUsuario = tipoUsuario,
            fotoPerfil = fotoPerfil,
            curso = curso,
            dataCadastro = dataCadastro,
            statusConta = if (ativo) "ATIVO" else "INATIVO",
            ativo = ativo
        )
    }
}