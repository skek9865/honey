package project.honey.business.service.manage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.manage.Tb410Dto;
import project.honey.business.form.manage.Search040201;
import project.honey.business.form.manage.Tb410Form;
import project.honey.business.form.manage.Tb410_1Form;
import project.honey.system.dto.CodeDto;

import java.util.List;

public interface Service040201 {

    Boolean insert(Tb410Form tb410Form, Tb410_1Form tb410_1Form);

    Page<Tb410Dto> findAll(Search040201 search040201, Pageable pageable);

    List<List<String>> findAllByExcel(Search040201 search040201);

    Tb410Dto findById(Integer id);

    Boolean update(Tb410Form tb410Form, Tb410_1Form tb410_1Form);

    Boolean delete(Integer id);

    List<CodeDto> findAllBySelect(Integer type);
}
