package project.honey.business.service.analyze;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.analaze.Dto040304;
import project.honey.business.form.analyze.Search040304;

import java.util.List;

public interface Service040304 {

    Page<Dto040304> findAllByDsl(Search040304 search040304, Pageable pageable);

    List<List<String>> findAllByExcel(Search040304 search040304);
}
