package project.honey.stock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.stock.dto.search.Search060101;
import project.honey.stock.entity.Tb601;

import java.util.List;

public interface Tb601RepositoryDsl {

    Page<Tb601> findAllByDsl(Search060101 search, Pageable pageable);

    List<Tb601> findAllByExcel(Search060101 search);
}
