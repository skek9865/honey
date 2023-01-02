package project.honey.business.service.sale;

import project.honey.business.dto.sale.Dto040403;
import project.honey.business.form.analyze.Search040307;

import java.util.List;

public interface Service040403 {

    List<Dto040403> findAllByDsl(Search040307 search040307);

    List<List<String>> findAllByExcel(Search040307 search040307);
}
