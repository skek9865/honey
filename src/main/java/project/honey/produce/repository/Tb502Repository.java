package project.honey.produce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.produce.entity.Tb501;
import project.honey.produce.entity.Tb502;

public interface Tb502Repository extends JpaRepository<Tb502, Integer>, Tb502RepositoryDsl {

}
