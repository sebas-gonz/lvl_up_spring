package com.lvlupspring.security;

import com.lvlupspring.entity.Usuario;
import com.lvlupspring.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.singletonList;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + username));

        GrantedAuthority autorizacion = new SimpleGrantedAuthority(usuario.getRol() != null ? usuario.getRol().getNombreRol() : "ROLE_USER");

        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                true,
                true,
                true,
                true,
                singletonList(autorizacion));
    }
}
