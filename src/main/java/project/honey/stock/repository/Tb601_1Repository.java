package project.honey.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.stock.entity.Tb601_1;

public interface Tb601_1Repository extends JpaRepository<Tb601_1, Integer>, Tb601_1RepositoryDsl {
}
