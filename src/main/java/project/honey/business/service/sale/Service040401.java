package project.honey.business.service.sale;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.sale.Tb415Dto;
import project.honey.business.dto.sale.Tb415MainDto;
import project.honey.business.dto.sale.Tb415_1Dto;
import project.honey.business.form.sale.Search040401;
import project.honey.business.form.sale.Tb415Form;
import project.honey.business.form.sale.Tb415_1Form;

import java.util.List;

public interface Service040401 {

    Boolean insert(Tb415Form tb415Form, List<Tb415_1Form> tb415_1Form);

    Page<Tb415MainDto> findAllByDsl(Search040401 search040401, Pageable pageable);

    List<List<String>> findAllByExcel(Search040401 search040401);

    Tb415Dto findById(Integer id);

    List<Tb415_1Dto> findChildByFk(Integer id);

    Long findOrderNo();

    Boolean update(Tb415Form tb415Form, List<Tb415_1Form> tb415_1Form);

    Boolean delete(Integer id);
}
