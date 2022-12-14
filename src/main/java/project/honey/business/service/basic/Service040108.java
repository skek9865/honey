package project.honey.business.service.basic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.basic.Tb402Dto;
import project.honey.business.form.basic.Tb402Form;

public interface Service040108 {

    Page<Tb402Dto> findAllByDsl(Pageable pageable);

    Tb402Dto findById(Integer id);

    Boolean update(Tb402Form form);
}
