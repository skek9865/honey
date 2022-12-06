package project.honey.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.business.entity.Tb405;

public interface Tb405Repository extends JpaRepository<Tb405, Integer>, Tb405RepositoryDsl {
}
