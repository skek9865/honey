package project.honey.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.business.entity.Tb409;

public interface Tb409Repository extends JpaRepository<Tb409, Integer>, Tb409RepositoryDsl {
}
