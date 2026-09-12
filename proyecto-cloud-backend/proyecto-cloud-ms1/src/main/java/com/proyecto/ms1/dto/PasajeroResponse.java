package com.proyecto.ms1.dto;

import com.proyecto.ms1.model.Sexo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PasajeroResponse {
    private Long id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private Sexo sexo;
    private String distrito;
}