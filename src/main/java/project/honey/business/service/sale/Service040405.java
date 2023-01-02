package project.honey.business.service.sale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.sale.Dto040405;
import project.honey.business.form.sale.Search040404;

import java.util.List;

public interface Service040405 {

    Page<Dto040405> findAllByDsl(Search040404 search040404, Pageable pageable);

    List<List<String>> findAllByExcel(Search040404 search040404);
}
