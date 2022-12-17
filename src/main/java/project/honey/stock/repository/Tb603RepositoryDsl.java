package project.honey.stock.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.stock.dto.search.Search060102;
import project.honey.stock.dto.search.Search060103;
import project.honey.stock.entity.Tb602;
import project.honey.stock.entity.Tb603;

import java.util.List;

public interface Tb603RepositoryDsl {

    Page<Tb603> findAllByDsl(Search060103 search, Pageable pageable);

    List<Tb603> findAllByExcel(Search060103 search);
}
