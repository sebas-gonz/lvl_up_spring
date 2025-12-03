package com.lvlupspring.service;

import com.lvlupspring.dto.AuthResponseDTO;
import com.lvlupspring.dto.LoginDTO;
import com.lvlupspring.dto.RegistroUsuarioDTO;
import com.lvlupspring.entity.Rol;
import com.lvlupspring.entity.Usuario;
import com.lvlupspring.repository.RolRepository;
import com.lvlupspring.repository.UsuarioRepository;
import com.lvlupspring.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final RolRepository rolRepository;

    public AuthResponseDTO registrar(RegistroUsuarioDTO registroDTO) {
        Usuario usuario = new Usuario();
        Rol rolUsuario = rolRepository.findByNombreRol("ROLE_USER").orElseGet(() -> {
            Rol nuevoRol = new Rol();
            nuevoRol.setNombreRol("ROLE_USER");
            return rolRepository.save(nuevoRol);
        });
        usuario.setRol(rolUsuario);
        usuario.setNombre(registroDTO.getNombre());
        usuario.setApellido(registroDTO.getApellido());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setFechaNacimiento(registroDTO.getFechaNacimiento());
        usuario.setRunUsuario(registroDTO.getRunUsuario());
        usuario.setDvUsuario(registroDTO.getDvUsuario());
        usuario.setFotoUsuario(registroDTO.getFotoUsuario());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));
        usuarioRepository.save(usuario);

        String token = jwtUtils.generarToken(usuario.getEmail());
        return new AuthResponseDTO(token);
    }

    public AuthResponseDTO login(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        String token = jwtUtils.generarToken(loginDTO.getEmail());
        return new AuthResponseDTO(token);
    }
}
