package com.lvlupspring.service;

import com.lvlupspring.dto.UsuarioDTO;
import com.lvlupspring.entity.Usuario;
import com.lvlupspring.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    public UsuarioDTO findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mapearDTO(usuario);
    }
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(this::mapearDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    public UsuarioDTO mapearDTO(Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuarioId(usuario.getUsuarioId());
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setApellido(usuario.getApellido());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setFotoUsuario(usuario.getFotoUsuario());
        usuarioDTO.setRol(usuario.getRol().getNombreRol());
        usuarioDTO.setDvUsuario(usuario.getDvUsuario());
        usuarioDTO.setRunUsuario(usuario.getRunUsuario());
        usuarioDTO.setFechaNacimiento(usuario.getFechaNacimiento());
        return usuarioDTO;
    }

    public UsuarioDTO actualizarPerfil(UsuarioDTO dto, String email){
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        usuario.setFotoUsuario(dto.getFotoUsuario());
        usuario.setEmail(dto.getEmail());
        Usuario usuarioActualizado = usuarioRepository.save(usuario);
        return mapearDTO(usuarioActualizado);
    }

}
