package project.honey.business.service.analyze;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.analaze.Dto040301;
import project.honey.business.form.analyze.Search040301;

import java.util.List;

public interface Service040301 {

    Page<Dto040301> findAllByDsl(Search040301 search040301, Pageable pageable);

    List<List<String>> findAllByExcel(Search040301 search040301);
}
