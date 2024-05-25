package dto;

import enums.StatoDispositivo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DispositivoDto {
    @Size(max = 50)
    @NotNull
    private String modello;
    @Size(max = 30)
    @NotNull
    private String tipoDispositivo;
    @NotNull
    private StatoDispositivo statoDispositivo;

    private Integer dipendenteId;
}
