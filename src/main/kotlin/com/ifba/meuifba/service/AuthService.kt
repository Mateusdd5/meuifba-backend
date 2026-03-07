package com.ifba.meuifba.service

import com.ifba.meuifba.dto.*
import com.ifba.meuifba.model.Usuario
import com.ifba.meuifba.repository.UsuarioRepository
import com.ifba.meuifba.repository.CursoRepository
import com.ifba.meuifba.repository.AreaConhecimentoRepository
import com.ifba.meuifba.security.JwtUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val usuarioRepository: UsuarioRepository,
    private val cursoRepository: CursoRepository,
    private val areaConhecimentoRepository: AreaConhecimentoRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil
) {

    fun login(request: LoginRequest): LoginResponse {
        val usuario = usuarioRepository.findByEmail(request.email)
            ?: throw RuntimeException("Email ou senha incorretos")

        if (!passwordEncoder.matches(request.senha, usuario.senha)) {
            throw RuntimeException("Email ou senha incorretos")
        }

        val token = jwtUtil.generateToken(usuario.email, usuario.id)

        return LoginResponse(
            token = token,
            usuario = usuario.toUsuarioResponse()
        )
    }

    fun register(request: RegisterRequest): UsuarioResponse {
        if (usuarioRepository.existsByEmail(request.email)) {
            throw RuntimeException("Email já cadastrado")
        }

        val usuario = Usuario(
            nome = request.nome,
            email = request.email,
            senha = passwordEncoder.encode(request.senha),
            tipoUsuario = request.tipoUsuario,
            matricula = request.matricula,
            cursoId = request.cursoId
        )

        val saved = usuarioRepository.save(usuario)
        return saved.toUsuarioResponse()
    }

    private fun Usuario.toUsuarioResponse(): UsuarioResponse {
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