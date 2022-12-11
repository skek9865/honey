package project.honey.business.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.Tb406Dto;
import project.honey.business.form.Tb406Form;
import project.honey.system.dto.CodeDto;

import java.util.List;

public interface Service040107 {

    Boolean insert(Tb406Form form);

    Page<Tb406Dto> findAllByDsl(Pageable pageable);

    List<List<String>> findAllByExcel();

    Tb406Dto findById(Integer id);

    Boolean update(Tb406Form form);

    Boolean delete(Integer id);

    List<CodeDto> findAllBySelect();
}
