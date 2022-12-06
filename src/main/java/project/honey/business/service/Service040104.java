package project.honey.business.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.Tb401Dto;
import project.honey.business.dto.Tb404Dto;
import project.honey.business.form.Tb401Form;
import project.honey.business.form.Tb404Form;
import project.honey.system.dto.CodeDto;

import java.util.List;
import java.util.Map;

public interface Service040104 {

    Boolean insert(Tb404Form form);

    Page<Tb404Dto> findAll(Pageable pageable);

    List<List<String>> findAllByExcel();

    Tb404Dto findById(Integer id);

    Boolean update(Tb404Form form);

    Boolean delete(Integer id);

    Map<String, String> findAllBySelect();
}
