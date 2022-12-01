package project.honey.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.honey.system.entity.Tb903;

import java.util.Optional;

public interface Tb903Repository extends JpaRepository<Tb903, Integer>, Tb903RepositoryDsl {

}
