package model;

import enums.TipoSmartphone;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Smartphone extends Dispositivo{
    private TipoSmartphone tipoSmartphone;
}
