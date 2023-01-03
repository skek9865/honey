package project.honey.company.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.company.dto.Tb106Dto;
import project.honey.company.dto.Tb106_1Dto;
import project.honey.company.form.Tb106Form;
import project.honey.company.form.Tb106_1Form;

import java.util.List;

public interface Service010301 {

    Boolean insert(Tb106Form form);

    Page<Tb106Dto> findAll(Pageable pageable);

    List<List<String>> findAllByExcel();

    Tb106Dto findById(Integer id);

    Boolean update(Tb106Form form);

    Boolean delete(Integer id);

    Boolean insertFk(Tb106_1Form form);

    List<Tb106_1Dto> findAllFK(Integer fk);

    List<List<String>> findAllByExcelFk(Integer fk);

    Tb106_1Dto findByIdFk(Integer id);

    Boolean updateFk(Tb106_1Form form);

    Boolean deleteFk(Integer id);
}
