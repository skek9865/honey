package project.honey.business.service.analyze;

import org.springframework.data.domain.Page;
import project.honey.business.dto.analaze.Dto040308;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface Service040308 {

    Page<Dto040308> findAllByDsl(String sYmd1, String sYmd2, Pageable pageable);

    List<List<String>> findAllByExcel(String sYmd1, String sYmd2);
}
