package model;

import enums.TipoSmartphone;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Smartphone extends Dispositivo{
    @Enumerated(EnumType.STRING)
    private TipoSmartphone tipoSmartphone;
}
