package project.honey.business.service.analyze;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.analaze.Dto040303;
import project.honey.business.form.analyze.Search040302;

import java.util.List;

public interface Service040303 {

    Page<Dto040303> findAllByDsl(Search040302 search040302, Pageable pageable);

    List<List<String>> findAllByExcel(Search040302 search040302);
}
