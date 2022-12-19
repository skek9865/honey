package project.honey.company.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.company.entity.Tb105;

import java.util.List;


public interface Tb105RepositoryDsl {

    List<Tb105> findAllByDsl();

    Page<Tb105> findAllByDsl(Pageable pageable);
}
