package project.honey.personDepart.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.personDepart.dto.Tb203Dto;
import project.honey.personDepart.form.Tb203Form;

import java.io.IOException;
import java.util.List;

public interface Service020201 {

    Boolean insert(Tb203Form form) throws IOException;

    Page<Tb203Dto> findAllByDsl(String outFNm, String part, Pageable pageable);

    List<List<String>> findAllByExcel(String outFNm, String part);

    Tb203Dto findById(Integer id);

    Boolean update(Tb203Form form);

    Boolean delete(Integer id);
}
