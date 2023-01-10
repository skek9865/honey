package project.honey.business.service.analyze;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.analaze.Dto040309;
import project.honey.business.form.analyze.Search040309;

import java.util.List;

public interface Service040309 {

    Page<Dto040309> findAllByDsl(Search040309 search040309, Pageable pageable);

    List<List<String>> findAllByExcel(Search040309 search040309);
}
