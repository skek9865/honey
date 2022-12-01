package project.honey.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.business.entity.Tb402;

public interface Tb402Repository extends JpaRepository<Tb402, Integer>, Tb402RepositoryDsl {
}
