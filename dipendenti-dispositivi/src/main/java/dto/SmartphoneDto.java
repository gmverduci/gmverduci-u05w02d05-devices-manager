package dto;

import enums.StatoDispositivo;
import enums.TipoSmartphone;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SmartphoneDto {
    @NotBlank
    private String marca;
    @NotBlank
    private String modello;
    private double schermo;
    @NotBlank
    private StatoDispositivo statoDispositivo;
    @NotBlank
    private TipoSmartphone tipoSmartphone;}
