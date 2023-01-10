package project.honey.business.repository.basic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.basic.Query405Dto;
import project.honey.business.dto.search.Search405;
import project.honey.business.dto.search.SearchPopUp405;
import project.honey.business.entity.basic.Tb405;
import project.honey.stock.dto.search.Search060201;

import java.util.List;

public interface Tb405RepositoryDsl {

    Page<Tb405> findAllByDsl(Search405 search, Pageable pageable);

    Page<Query405Dto> findAllByQuery(Search060201 search, Pageable pageable);

    List<Query405Dto> findAllByQueryExcel(Search060201 search);

    List<Tb405> findAllByPopUp(SearchPopUp405 search);

    List<Tb405> findAllByExcel(Search405 search);
}
