package project.honey.company.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.company.dto.Tb107Dto;
import project.honey.company.form.Tb107Form;

import java.util.List;

public interface Service010302 {

    Boolean insert(Tb107Form form);

    Page<Tb107Dto> findAll(Pageable pageable);

    List<List<String>> findAllByExcel();

    Tb107Dto findById(Integer id);

    Boolean update(Tb107Form form);

    Boolean delete(Integer id);
}
