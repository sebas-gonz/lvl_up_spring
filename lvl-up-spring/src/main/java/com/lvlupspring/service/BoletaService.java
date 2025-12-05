package com.lvlupspring.service;

import com.lvlupspring.dto.BoletaDTO;
import com.lvlupspring.dto.DetalleDTO;
import com.lvlupspring.entity.*;
import com.lvlupspring.repository.BoletaRepository;
import com.lvlupspring.repository.DireccionRepository;
import com.lvlupspring.repository.ProductoRepository;
import com.lvlupspring.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoletaService {
    private final BoletaRepository boletaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final DireccionRepository direccionRepository;

    @Transactional
    public Boleta crearBoleta(BoletaDTO boletaDTO, String emailUsuario) {
        Boleta nueva = new Boleta();
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        nueva.setUsuario(usuario);
        nueva.setCalle(boletaDTO.getCalle());
        nueva.setNumeroDepto(boletaDTO.getNumeroDepto());
        nueva.setIndicaciones(boletaDTO.getIndicaciones());
        nueva.setComuna(boletaDTO.getComuna());
        nueva.setTotalBoleta(boletaDTO.getTotalBoleta());

        List<DetalleBoleta> detalles = new ArrayList<>();
        Integer totalCalculado = 0;

        for (DetalleDTO detalle : boletaDTO.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            if (producto.getStock() < detalle.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombreProducto());
            }
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);
            DetalleBoleta detalleBoleta = new DetalleBoleta();
            detalleBoleta.setCantidad(detalle.getCantidad());
            detalleBoleta.setProducto(producto);
            detalleBoleta.setBoleta(nueva);
            Integer precioFinal = (producto.getOferta() != null && producto.getOferta())
                    ? producto.getPrecioOferta()
                    : producto.getPrecio();

            Integer subTotal = precioFinal * detalle.getCantidad();
            detalleBoleta.setSubTotal(subTotal);

            totalCalculado += subTotal;
            detalles.add(detalleBoleta);
        }
        nueva.setTotalBoleta(totalCalculado);
        nueva.setDetalles(detalles);
        return boletaRepository.save(nueva);
    }

    public List<Boleta> findAll(){
        return boletaRepository.findAll();
    }

    public Boleta findById(Long boletaId){
        return boletaRepository.findById(boletaId).orElse(null);
    }

    public List<Boleta> obtenerHistorialUsuario(Long usuarioId) {
        return boletaRepository.findByUsuario_UsuarioId(usuarioId);
    }

}
