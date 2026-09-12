package com.proyecto.ms1.service;

import com.proyecto.ms1.dto.TarjetaRequest;
import com.proyecto.ms1.dto.TarjetaResponse;
import com.proyecto.ms1.dto.TarjetaUpdateRequest;
import com.proyecto.ms1.exception.NotFoundException;
import com.proyecto.ms1.model.Pasajero;
import com.proyecto.ms1.model.Tarjeta;
import com.proyecto.ms1.model.TipoTarjeta;
import com.proyecto.ms1.repository.PasajeroRepository;
import com.proyecto.ms1.repository.TarjetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TarjetaService {

    private final TarjetaRepository tarjetaRepository;
    private final PasajeroRepository pasajeroRepository;

    public TarjetaResponse crearTarjeta(TarjetaRequest request) {
        Pasajero pasajero = pasajeroRepository.findById(request.getPasajeroId())
                .orElseThrow(() -> new NotFoundException("Pasajero " + request.getPasajeroId() + " no encontrado"));

        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setPasajero(pasajero);
        tarjeta.setTipo(request.getTipo());
        tarjeta.setSaldo(request.getSaldo());

        LocalDate fechaEmision = LocalDate.now();
        tarjeta.setFechaEmision(fechaEmision);
        tarjeta.setFechaVencimiento(calcularVencimiento(request.getTipo(), fechaEmision));

        return toResponse(tarjetaRepository.save(tarjeta));
    }

    public TarjetaResponse obtenerTarjeta(Long id) {
        Tarjeta tarjeta = tarjetaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tarjeta " + id + " no encontrada"));
        return toResponse(tarjeta);
    }

    public List<TarjetaResponse> obtenerTarjetasBatch(List<Long> ids) {
        return tarjetaRepository.findAllById(ids).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<TarjetaResponse> obtenerTarjetasPorPasajero(Long pasajeroId) {
        if (!pasajeroRepository.existsById(pasajeroId)) {
            throw new NotFoundException("Pasajero " + pasajeroId + " no encontrado");
        }
        return tarjetaRepository.findByPasajeroId(pasajeroId).stream()
                .map(this::toResponse)
                .toList();
    }

    public TarjetaResponse actualizarTarjeta(Long id, TarjetaUpdateRequest request) {
    Tarjeta tarjeta = tarjetaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Tarjeta " + id + " no encontrada"));

    tarjeta.setSaldo(request.getSaldo());

    return toResponse(tarjetaRepository.save(tarjeta));
}

    public void eliminarTarjeta(Long id) {
        if (!tarjetaRepository.existsById(id)) {
            throw new NotFoundException("Tarjeta " + id + " no encontrada");
        }
        tarjetaRepository.deleteById(id);
    }

    private LocalDate calcularVencimiento(TipoTarjeta tipo, LocalDate fechaEmision) {
        boolean tieneVencimiento = tipo == TipoTarjeta.ESTUDIANTE
                || tipo == TipoTarjeta.ESCOLAR
                || tipo == TipoTarjeta.ESPECIAL;
        return tieneVencimiento ? fechaEmision.plusYears(1) : null;
    }

    private TarjetaResponse toResponse(Tarjeta t) {
        return new TarjetaResponse(
                t.getId(),
                t.getPasajero().getId(),
                t.getFechaEmision(),
                t.getFechaVencimiento(),
                t.getTipo(),
                t.getSaldo()
        );
    }
}