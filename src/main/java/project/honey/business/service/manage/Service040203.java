package project.honey.business.service.manage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.manage.*;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.form.manage.*;

import java.util.List;

public interface Service040203 {

    Boolean insert(Tb412Form tb412Form, List<Tb412_1Form> tb412_1Form);

    Page<Tb412MainDto> findAllByDsl(Search040203 search040203, Pageable pageable);

    List<List<String>> findAllByExcel(Search040203 search040203);

    Tb412Dto findById(Integer id);

    List<Tb412_1Dto> findChildByFk(Integer id);

    Long findSaleNo();

    Boolean update(Tb412Form tb412Form, List<Tb412_1Form> tb412_1Form);

    Boolean delete(Integer id);

    List<Tb412MainDto> findAllByPopUp(SearchPopUp410 searchPopUp410);
}
