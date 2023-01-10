package project.honey.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.honey.system.dto.Tb906Dto;
import project.honey.system.entity.Tb906;

import java.util.List;

public interface Tb906Repository extends JpaRepository<Tb906, Integer>, Tb906RepositoryDsl {

}
