package com.lvlupspring.config;

import com.lvlupspring.entity.Rol;
import com.lvlupspring.entity.Usuario;
import com.lvlupspring.repository.RolRepository;
import com.lvlupspring.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- Verificando existencia de Admin ---");

        // 1. Asegurar que existan los ROLES
        Rol rolAdmin = rolRepository.findByNombreRol("ROLE_ADMIN")
                .orElseGet(() -> {
                    Rol nuevoRol = new Rol();
                    nuevoRol.setNombreRol("ROLE_ADMIN");
                    return rolRepository.save(nuevoRol);
                });
        String emailAdmin = "admin@lvlup.cl";

        if (!usuarioRepository.existsByEmail(emailAdmin)) {
            Usuario admin = new Usuario();
            admin.setNombre("Super");
            admin.setApellido("Admin");
            admin.setEmail(emailAdmin);
            admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña encriptada
            admin.setRol(rolAdmin);
            admin.setFechaNacimiento(LocalDate.of(1990, 1, 1));
            admin.setRunUsuario(2222222);
            admin.setDvUsuario("K");
            admin.setFotoUsuario("https://res.cloudinary.com/dsfuxaywv/image/upload/v1764887905/adxcngmx5d0bt2nlpzli.jpg");

            usuarioRepository.save(admin);
            System.out.println("Usuario ADMIN creado: " + emailAdmin + " / admin123");
        } else {
            System.out.println("El usuario ADMIN ya existe.");
        }
    }
}
