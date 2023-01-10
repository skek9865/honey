package project.honey.business.service.basic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.search.Search405;
import project.honey.business.dto.search.SearchPopUp405;
import project.honey.business.dto.basic.Tb405Dto;
import project.honey.business.form.basic.Tb405Form;

import java.io.IOException;
import java.util.List;

public interface Service040105 {

    Boolean insert(Tb405Form form) throws IOException;

    Page<Tb405Dto> findAll(Search405 search, Pageable pageable);

    Tb405Dto findById(Integer id);

    Boolean update(Tb405Form form);

    Boolean delete(Integer id);

    List<List<String>> findAllByExcel(Search405 search);

    List<Tb405Dto> findAllByPopUp(SearchPopUp405 search);
}
