package com.lvlupspring.validator;

import com.lvlupspring.dto.RegistroUsuarioDTO;
import com.lvlupspring.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class UsuarioValidator implements Validator {

    private final UsuarioRepository usuarioRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistroUsuarioDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistroUsuarioDTO dto = (RegistroUsuarioDTO) target;
        if (!dto.getPassword().equals(dto.getPasswordConfirmation())) {
            errors.rejectValue("passwordConfirmation", "PasswordsDoNotMatch", "Las contraseñas no coinciden");
        }

        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            errors.rejectValue("email", "EmailExists", "El correo ya está registrado");
        }

        if (usuarioRepository.existsByRunUsuario(dto.getRunUsuario())) {
            errors.rejectValue("runUsuario", "RunExists", "El RUN ya está registrado");
        }
    }
}
