package project.honey.personDepart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.personDepart.dto.Tb204Dto;
import project.honey.personDepart.form.Tb204Form;

import java.io.IOException;
import java.util.List;

public interface Service020202 {

    Boolean insert(Tb204Form form) throws IOException;

    Page<Tb204Dto> findAll(String outFNm, String part, Pageable pageable);

    List<Tb204Dto> findAllByExcel(String outFNm, String part);

    Tb204Dto findById(Integer id);

    Boolean update(Tb204Form form);

    Boolean delete(Integer id);
}
