package project.honey.business.service.sale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.sale.Tb416Dto;
import project.honey.business.dto.sale.Tb416MainDto;
import project.honey.business.dto.sale.Tb416_1Dto;
import project.honey.business.form.sale.Search040402;
import project.honey.business.form.sale.Tb416Form;
import project.honey.business.form.sale.Tb416_1Form;

import java.util.List;

public interface Service040402 {

    Boolean insert(Tb416Form tb416Form, List<Tb416_1Form> tb416_1Form);

    Page<Tb416MainDto> findAllByDsl(Search040402 search040402, Pageable pageable);

    List<List<String>> findAllByExcel(Search040402 search040402);

    Tb416Dto findById(Integer id);

    List<Tb416_1Dto> findChildByFk(Integer id);

    Long findBuyNo();

    Boolean update(Tb416Form tb416Form, List<Tb416_1Form> tb416_1Form);

    Boolean delete(Integer id);
}
