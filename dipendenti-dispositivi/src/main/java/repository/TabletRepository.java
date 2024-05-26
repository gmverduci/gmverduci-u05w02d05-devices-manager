package repository;


import model.Tablet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TabletRepository extends JpaRepository<Tablet, Integer> {
}
