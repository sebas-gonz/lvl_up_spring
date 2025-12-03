package com.lvlupspring.service;

import com.lvlupspring.dto.DireccionDTO;
import com.lvlupspring.entity.Comuna;
import com.lvlupspring.entity.Direccion;
import com.lvlupspring.entity.Usuario;
import com.lvlupspring.repository.ComunaRepository;
import com.lvlupspring.repository.DireccionRepository;
import com.lvlupspring.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DireccionService {

    private  final DireccionRepository direccionRepository;
    private  final UsuarioRepository usuarioRepository;
    private final ComunaRepository comunaRepository;
    public Direccion save(Direccion direccion) {
        return direccionRepository.save(direccion);
    }

    public Direccion crearDireccion(String emailUsuario, DireccionDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Comuna comuna = comunaRepository.findByNombreComuna(dto.getComuna()).orElseThrow(() -> new RuntimeException("Comuna no encontrada"));

        Direccion direccion = new Direccion();
        direccion.setCalle(dto.getCalle());
        direccion.setNumeroDepto(dto.getNumeroDepto());
        direccion.setUsuario(usuario);
        direccion.setComuna(comuna);
        return direccionRepository.save(direccion);
    }

    public void deleteById(Long id) {
        direccionRepository.deleteById(id);
    }

    public DireccionDTO mapearDTO(Direccion direccion) {
        DireccionDTO direccionDTO = new DireccionDTO();
        direccionDTO.setCalle(direccion.getCalle());
        direccionDTO.setNumeroDepto(direccion.getNumeroDepto());
        direccionDTO.setComuna(direccion.getComuna().getNombreComuna());
        direccionDTO.setIndicaciones(direccion.getIndicaciones());
        return direccionDTO;
    }

    public List<DireccionDTO> findByUsuarioEmail(String email) {
        List<Direccion> direcciones = direccionRepository.findByUsuario_Email(email);
        return direcciones.stream().map(this::mapearDTO).toList();
    }
}
