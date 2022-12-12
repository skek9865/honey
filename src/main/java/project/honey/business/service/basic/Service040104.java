package project.honey.business.service.basic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.basic.Tb404Dto;
import project.honey.business.form.basic.Tb404Form;

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
