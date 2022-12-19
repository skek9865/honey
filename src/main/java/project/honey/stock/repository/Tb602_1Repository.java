package project.honey.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.stock.entity.Tb601_1;
import project.honey.stock.entity.Tb602_1;

public interface Tb602_1Repository extends JpaRepository<Tb602_1, Integer>, Tb602_1RepositoryDsl {
}
