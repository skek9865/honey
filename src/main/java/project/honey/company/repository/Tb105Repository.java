package project.honey.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.company.entity.Tb105;


public interface Tb105Repository extends JpaRepository<Tb105, Integer> , Tb105RepositoryDsl {

}
