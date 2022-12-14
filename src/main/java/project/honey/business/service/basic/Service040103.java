package project.honey.business.service.basic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.basic.Tb403Dto;
import project.honey.business.form.basic.Tb403Form;
import project.honey.system.dto.CodeDto;

import java.util.List;

public interface Service040103 {

    Boolean insert(Tb403Form form);

    Page<Tb403Dto> findAllByDsl(Pageable pageable);

    List<List<String>> findAllByExcel();

    Tb403Dto findById(Integer id);

    Boolean update(Tb403Form form);

    Boolean delete(Integer id);

    List<CodeDto> findAllBySelect();
}
