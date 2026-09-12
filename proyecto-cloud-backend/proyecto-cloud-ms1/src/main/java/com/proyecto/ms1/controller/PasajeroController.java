package com.proyecto.ms1.controller;

import com.proyecto.ms1.dto.PasajeroRequest;
import com.proyecto.ms1.dto.PasajeroResponse;
import com.proyecto.ms1.dto.TarjetaResponse;
import com.proyecto.ms1.service.PasajeroService;
import com.proyecto.ms1.service.TarjetaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/pasajeros")
@RequiredArgsConstructor
public class PasajeroController {

    private final PasajeroService pasajeroService;
    private final TarjetaService tarjetaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PasajeroResponse crearPasajero(@Valid @RequestBody PasajeroRequest request) {
        return pasajeroService.crearPasajero(request);
    }

    @GetMapping
    public List<PasajeroResponse> listarPasajeros() {
        return pasajeroService.listarPasajeros();
    }

    @GetMapping("/batch")
    public List<PasajeroResponse> obtenerPasajerosBatch(@RequestParam String ids) {
        List<Long> listaIds = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return pasajeroService.obtenerPasajerosBatch(listaIds);
    }

    @GetMapping("/{id}")
    public PasajeroResponse obtenerPasajero(@PathVariable Long id) {
        return pasajeroService.obtenerPasajero(id);
    }

    @GetMapping("/{id}/tarjetas")
    public List<TarjetaResponse> obtenerTarjetasDePasajero(@PathVariable Long id) {
        return tarjetaService.obtenerTarjetasPorPasajero(id);
    }

    @PutMapping("/{id}")
    public PasajeroResponse actualizarPasajero(@PathVariable Long id, @Valid @RequestBody PasajeroRequest request) {
        return pasajeroService.actualizarPasajero(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarPasajero(@PathVariable Long id) {
        pasajeroService.eliminarPasajero(id);
    }
}