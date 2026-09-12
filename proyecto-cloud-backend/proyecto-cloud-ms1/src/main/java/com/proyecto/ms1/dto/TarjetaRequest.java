package com.proyecto.ms1.dto;

import com.proyecto.ms1.model.TipoTarjeta;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TarjetaRequest {

    @NotNull(message = "El pasajero_id es obligatorio")
    private Long pasajeroId;

    @NotNull(message = "El tipo de tarjeta es obligatorio")
    private TipoTarjeta tipo;

    @NotNull(message = "El saldo inicial es obligatorio")
    private BigDecimal saldo;
}