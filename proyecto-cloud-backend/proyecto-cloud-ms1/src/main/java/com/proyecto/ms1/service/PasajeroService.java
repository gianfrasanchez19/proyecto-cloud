package com.proyecto.ms1.service;

import com.proyecto.ms1.dto.PasajeroRequest;
import com.proyecto.ms1.dto.PasajeroResponse;
import com.proyecto.ms1.exception.NotFoundException;
import com.proyecto.ms1.model.Pasajero;
import com.proyecto.ms1.repository.PasajeroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PasajeroService {

    private final PasajeroRepository pasajeroRepository;

    public PasajeroResponse crearPasajero(PasajeroRequest request) {
        Pasajero pasajero = new Pasajero();
        pasajero.setNombre(request.getNombre());
        pasajero.setFechaNacimiento(request.getFechaNacimiento());
        pasajero.setSexo(request.getSexo());
        pasajero.setDistrito(request.getDistrito());

        Pasajero guardado = pasajeroRepository.save(pasajero);
        return toResponse(guardado);
    }

    public PasajeroResponse obtenerPasajero(Long id) {
        Pasajero pasajero = pasajeroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pasajero " + id + " no encontrado"));
        return toResponse(pasajero);
    }

    public List<PasajeroResponse> listarPasajeros() {
        return pasajeroRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<PasajeroResponse> obtenerPasajerosBatch(List<Long> ids) {
        return pasajeroRepository.findAllById(ids).stream()
                .map(this::toResponse)
                .toList();
    }

    public PasajeroResponse actualizarPasajero(Long id, PasajeroRequest request) {
        Pasajero pasajero = pasajeroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pasajero " + id + " no encontrado"));

        pasajero.setNombre(request.getNombre());
        pasajero.setFechaNacimiento(request.getFechaNacimiento());
        pasajero.setSexo(request.getSexo());
        pasajero.setDistrito(request.getDistrito());

        return toResponse(pasajeroRepository.save(pasajero));
    }

    public void eliminarPasajero(Long id) {
        if (!pasajeroRepository.existsById(id)) {
            throw new NotFoundException("Pasajero " + id + " no encontrado");
        }
        pasajeroRepository.deleteById(id);
    }

    private PasajeroResponse toResponse(Pasajero p) {
        return new PasajeroResponse(p.getId(), p.getNombre(), p.getFechaNacimiento(), p.getSexo(), p.getDistrito());
    }
}