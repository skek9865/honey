package project.honey.company.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.company.entity.Tb105;


public interface Tb105RepositoryDsl {

    Page<Tb105> findAllByDsl(Pageable pageable);
}
