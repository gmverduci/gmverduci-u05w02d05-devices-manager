package repository;

import model.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispositivoRepository extends JpaRepository<Dispositivo, Integer> {
}
