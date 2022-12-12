package project.honey.business.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.Tb409Dto;
import project.honey.business.form.Tb409Form;
import project.honey.system.dto.CodeDto;

import java.util.List;

public interface Service040110 {

    Boolean insert(Tb409Form form);

    Page<Tb409Dto> findAllByDsl(Pageable pageable);

    List<List<String>> findAllByExcel();

    Tb409Dto findById(Integer id);

    Boolean update(Tb409Form form);

    Boolean delete(Integer id);


    List<CodeDto> findAllBySelect();
}
