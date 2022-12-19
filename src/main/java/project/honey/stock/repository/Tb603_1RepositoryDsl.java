package project.honey.stock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.stock.dto.search.Search060107;
import project.honey.stock.entity.Tb603_1;

import java.util.List;

public interface Tb603_1RepositoryDsl {

    Page<Tb603_1> findAllByDsl(Search060107 search, Pageable pageable);

    List<Tb603_1> findAllByExcel(Search060107 search);
}
