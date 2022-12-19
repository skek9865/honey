package project.honey.business.service.basic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.basic.Tb408Dto;
import project.honey.business.form.basic.Tb408Form;
import project.honey.system.dto.CodeDto;

import java.util.List;

public interface Service040109 {

    Boolean insert(Tb408Form form);

    Page<Tb408Dto> findAllByDsl(Pageable pageable);

    List<List<String>> findAllByExcel();

    Tb408Dto findById(Integer id);

    Boolean update(Tb408Form form);

    Boolean delete(Integer id);

    List<CodeDto> findAllBySelect();
}
