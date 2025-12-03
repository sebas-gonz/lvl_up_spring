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
        Direccion direccion = direccionRepository.findById(boletaDTO.getDireccionId()).orElseThrow( () ->new RuntimeException("Dirección no encontrada"));
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario).orElseThrow( () ->new RuntimeException("Usuario no encontrado"));

        if (!direccion.getUsuario().getUsuarioId().equals(usuario.getUsuarioId())) {
            throw new RuntimeException("La dirección no pertenece al usuario autenticado");
        }

        List<DetalleBoleta> detalles = new ArrayList<>();
        Integer totalBoleta = 0;

        nueva.setUsuario(usuario);
        nueva.setIndicaciones(direccion.getIndicaciones());
        nueva.setNumeroDepto(direccion.getNumeroDepto());
        nueva.setCalle(direccion.getCalle());
        nueva.setComuna(direccion.getComuna().getNombreComuna());
        for(DetalleDTO detalle: boletaDTO.getItems()){
            Producto producto = productoRepository.findById(detalle.getProductoId()).orElseThrow( () ->new RuntimeException("Producto no encontrado"));
            DetalleBoleta detalleBoleta = new DetalleBoleta();
            detalleBoleta.setCantidad(detalle.getCantidad());
            detalleBoleta.setProducto(producto);
            detalleBoleta.setBoleta(nueva);

            if(producto.getStock() < detalle.getCantidad()){
                throw new RuntimeException("Stock insuficiente para el producto: " + producto.getNombreProducto());
            }
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);

            Integer subTotal;
            subTotal = producto.getOferta() != true ? producto.getPrecio() * detalle.getCantidad() : producto.getPrecioOferta() * detalle.getCantidad();

            detalleBoleta.setSubTotal(subTotal);
            totalBoleta += subTotal;
            detalles.add(detalleBoleta);
        }
        nueva.setTotalBoleta(totalBoleta);
        nueva.setDetalles(detalles);
        return boletaRepository.save(nueva);
    }

    public List<Boleta> findAll(){
        return boletaRepository.findAll();
    }

    public Boleta findById(Long boletaId){
        return boletaRepository.findById(boletaId).orElse(null);
    }

}
