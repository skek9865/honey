package project.honey.business.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.Tb402Dto;
import project.honey.business.form.Tb402Form;

import java.util.List;

public interface Service040108 {

    Page<Tb402Dto> findAllByDsl(Pageable pageable);

    Tb402Dto findById(Integer id);

    Boolean update(Tb402Form form);
}
