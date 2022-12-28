package project.honey.business.service.analyze;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.analaze.Dto040306;
import project.honey.business.dto.analaze.PrintData040306;
import project.honey.business.form.analyze.Search040306;

import java.util.List;

public interface Service040306 {

    Page<Dto040306> findAllByDsl(Search040306 search040306, Pageable pageable);

    List<List<String>> findAllByExcel(Search040306 search040306);

    PrintData040306 findPrintData(Integer id);
}
