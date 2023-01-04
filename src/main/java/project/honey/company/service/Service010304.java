package project.honey.company.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.company.dto.Tb109Dto;
import project.honey.company.form.Tb109Form;

import java.util.List;

public interface Service010304 {

    Boolean insert(Tb109Form form);

    Page<Tb109Dto> findAll(Pageable pageable);

    List<List<String>> findAllByExcel();

    Tb109Dto findById(Integer id);

    Boolean update(Tb109Form form);

    Boolean delete(Integer id);
}
