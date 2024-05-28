package dto;

import enums.StatoDispositivo;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.BooleanFlag;
import lombok.Data;

@Data
public class TabletDto {
    @NotBlank
    private String marca;
    @NotBlank
    private String modello;
    private double schermo;
    @NotBlank
    private String statoDispositivo;
    @BooleanFlag
    private boolean simInternet;
}
