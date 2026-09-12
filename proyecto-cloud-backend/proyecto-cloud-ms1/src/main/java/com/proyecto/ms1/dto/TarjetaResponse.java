package com.proyecto.ms1.dto;

import com.proyecto.ms1.model.TipoTarjeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TarjetaResponse {
    private Long id;
    private Long pasajeroId;
    private LocalDate fechaEmision;
    private LocalDate fechaVencimiento;
    private TipoTarjeta tipo;
    private BigDecimal saldo;
}