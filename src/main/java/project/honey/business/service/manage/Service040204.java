package project.honey.business.service.manage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.manage.*;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.form.manage.*;

import java.util.List;

public interface Service040204 {

    Boolean insert(Tb413Form tb413Form, List<Tb413_1Form> tb413_1Form);

    Page<Tb413MainDto> findAllByDsl(Search040204 search040204, Pageable pageable);

    List<List<String>> findAllByExcel(Search040204 search040204);

    Tb413Dto findById(Integer id);

    List<Tb413_1Dto> findChildByFk(Integer id);

    Long findShipNo();

    Boolean update(Tb413Form tb413Form, List<Tb413_1Form> tb413_1Form);

    Boolean delete(Integer id);

    List<Tb413MainDto> findAllByPopUp(SearchPopUp410 searchPopUp410);
}
