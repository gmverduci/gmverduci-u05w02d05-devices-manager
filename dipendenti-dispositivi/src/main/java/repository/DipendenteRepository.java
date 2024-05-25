package repository;

import model.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DipendenteRepository extends JpaRepository<Dipendente, Integer> {
}
