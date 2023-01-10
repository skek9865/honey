package project.honey.business.service.analyze;

import project.honey.business.dto.analaze.Dto040307;
import project.honey.business.form.analyze.Search040307;

import java.util.List;

public interface Service040307 {

    List<Dto040307> findAllByDsl(Search040307 search040307);

    List<List<String>> findAllByExcel(Search040307 search040307);
}
