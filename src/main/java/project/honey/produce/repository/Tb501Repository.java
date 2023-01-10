package project.honey.produce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.honey.produce.entity.Tb501;

import java.util.List;

public interface Tb501Repository extends JpaRepository<Tb501, Integer>, Tb501RepositoryDsl {

}
