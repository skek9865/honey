package project.honey.produce.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.search.Search050302;
import project.honey.produce.entity.Tb506;

import java.util.List;

public interface Tb506RepositoryDsl {

    Page<Tb506> findAllByDsl(Search050302 search, Pageable pageable);

    List<Tb506> findAllByExcel(Search050302 search);
}
