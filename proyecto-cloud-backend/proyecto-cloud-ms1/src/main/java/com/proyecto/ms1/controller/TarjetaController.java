package com.proyecto.ms1.controller;

import com.proyecto.ms1.dto.TarjetaRequest;
import com.proyecto.ms1.dto.TarjetaResponse;
import com.proyecto.ms1.dto.TarjetaUpdateRequest;
import com.proyecto.ms1.service.TarjetaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/tarjetas")
@RequiredArgsConstructor
public class TarjetaController {

    private final TarjetaService tarjetaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TarjetaResponse crearTarjeta(@Valid @RequestBody TarjetaRequest request) {
        return tarjetaService.crearTarjeta(request);
    }

    @GetMapping("/batch")
    public List<TarjetaResponse> obtenerTarjetasBatch(@RequestParam String ids) {
        List<Long> listaIds = Arrays.stream(ids.split(","))
                .map(Long::parseLong)
                .toList();
        return tarjetaService.obtenerTarjetasBatch(listaIds);
    }

    @GetMapping("/{id}")
    public TarjetaResponse obtenerTarjeta(@PathVariable Long id) {
        return tarjetaService.obtenerTarjeta(id);
    }

    @PutMapping("/{id}")
    public TarjetaResponse actualizarTarjeta(@PathVariable Long id, @Valid @RequestBody TarjetaUpdateRequest request) {
        return tarjetaService.actualizarTarjeta(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarTarjeta(@PathVariable Long id) {
        tarjetaService.eliminarTarjeta(id);
    }
}