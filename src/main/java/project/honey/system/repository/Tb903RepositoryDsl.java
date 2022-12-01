package project.honey.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.system.entity.Tb903;

public interface Tb903RepositoryDsl {

    Page<Tb903> findAllByDsl(Pageable pageable);
}
