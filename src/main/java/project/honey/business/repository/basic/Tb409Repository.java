package project.honey.business.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.business.entity.basic.Tb409;

public interface Tb409Repository extends JpaRepository<Tb409, Integer>, Tb409RepositoryDsl {
}
