package project.honey.company.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.company.dto.Tb108Dto;
import project.honey.company.form.Tb108Form;

import java.io.IOException;
import java.util.List;

public interface Service010303 {

    Boolean insert(Tb108Form form) throws IOException;

    Page<Tb108Dto> findAll(Pageable pageable);

    List<List<String>> findAllByExcel();

    Tb108Dto findById(Integer id);

    Boolean update(Tb108Form form) throws IOException;

    Boolean delete(Integer id);
}
