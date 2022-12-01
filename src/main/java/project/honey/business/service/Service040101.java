package project.honey.business.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.Tb401Dto;
import project.honey.business.form.Tb401Form;

import java.util.List;

public interface Service040101 {

    Boolean insert(Tb401Form form);

    Page<Tb401Dto> findAll(Pageable pageable);

    List<Tb401Dto> findAllByExcel();

    Tb401Dto findById(Integer id);

    Boolean update(Tb401Form form);

    Boolean delete(Integer id);
}
