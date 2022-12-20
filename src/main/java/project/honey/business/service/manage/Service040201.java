package project.honey.business.service.manage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.manage.PrintData040201;
import project.honey.business.dto.manage.Tb410Dto;
import project.honey.business.dto.manage.Tb410MainDto;
import project.honey.business.dto.manage.Tb410_1Dto;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.form.manage.Search040201;
import project.honey.business.form.manage.Tb410Form;
import project.honey.business.form.manage.Tb410_1Form;

import java.util.List;

public interface Service040201 {

    Boolean insert(Tb410Form tb410Form, List<Tb410_1Form> tb410_1Form);

    Page<Tb410MainDto> findAllByDsl(Search040201 search040201, Pageable pageable);

    List<List<String>> findAllByExcel(Search040201 search040201);

    Tb410Dto findById(Integer id);

    List<Tb410_1Dto> findChildByFk(Integer id);

    Long findEstNo();

    Boolean update(Tb410Form tb410Form, List<Tb410_1Form> tb410_1Form);

    Boolean delete(Integer id);

    PrintData040201 findPrintData(Integer id);

    List<Tb410MainDto> findAllByPopUp(SearchPopUp410 searchPopUp410);
}
