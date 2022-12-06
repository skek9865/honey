package project.honey.business.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.Search040105;
import project.honey.business.dto.Tb405Dto;
import project.honey.business.form.Tb405Form;

import java.io.IOException;
import java.util.List;

public interface Service040105 {

    Boolean insert(Tb405Form form) throws IOException;

    Page<Tb405Dto> findAll(Search040105 search, Pageable pageable);

    Tb405Dto findById(Integer id);

    Boolean update(Tb405Form form);

    Boolean delete(Integer id);

    List<List<String>> findAllByExcel(Search040105 search);
}
