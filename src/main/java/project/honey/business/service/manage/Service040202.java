package project.honey.business.service.manage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.manage.*;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.form.manage.Search040201;
import project.honey.business.form.manage.Tb411Form;
import project.honey.business.form.manage.Tb411_1Form;

import java.util.List;

public interface Service040202 {

    Boolean insert(Tb411Form tb411Form, List<Tb411_1Form> tb411_1Form);

    Page<Tb411MainDto> findAllByDsl(Search040201 search040201, Pageable pageable);

    List<List<String>> findAllByExcel(Search040201 search040201);

    Tb411Dto findById(Integer id);

    List<Tb411_1Dto> findChildByFk(Integer id);

    Long findOrderNo();

    Boolean update(Tb411Form tb411Form, List<Tb411_1Form> tb411_1Form);

    Boolean delete(Integer id);

    PrintData040202 findPrintData(Integer id);

    List<Tb411MainDto> findAllByPopUp(SearchPopUp410 searchPopUp410);
}
