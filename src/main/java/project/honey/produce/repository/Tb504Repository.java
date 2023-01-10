package project.honey.produce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.produce.entity.Tb504;

public interface Tb504Repository extends JpaRepository<Tb504, Integer>, Tb504RepositoryDsl {

    int countByWorkDt(String workDt);
}
