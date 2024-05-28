package dto;

import enums.StatoDispositivo;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LaptopDto {
    @NotBlank
    private String marca;
    @NotBlank
    private String modello;
    private double schermo;
    @NotBlank
    private String statoDispositivo;
    private int ram;
}
