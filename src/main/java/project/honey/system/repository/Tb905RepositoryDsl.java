package project.honey.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.system.entity.Tb905;

public interface Tb905RepositoryDsl {

    Page<Tb905> findAllByDsl(Pageable pageable);
}
