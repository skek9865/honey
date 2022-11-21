package project.honey.personDepart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.honey.personDepart.entity.Tb201;

public interface Tb201Repository extends JpaRepository<Tb201, Integer>, Tb201RepositoryDsl {
}
