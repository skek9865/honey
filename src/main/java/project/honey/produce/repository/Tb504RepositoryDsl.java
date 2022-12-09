package project.honey.produce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.search.Search050201;
import project.honey.produce.entity.Tb504;

public interface Tb504RepositoryDsl {

    Page<Tb504> findAllByDsl(Search050201 search, Pageable pageable);
}
