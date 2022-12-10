package project.honey.produce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Tb504Dto;
import project.honey.produce.dto.form.Tb504Form;
import project.honey.produce.dto.search.Search050201;

import java.util.List;

public interface Service050201 {

    Integer insert(Tb504Form dto);

    Integer update(Tb504Form dto);

    Page<Tb504Dto> findAll(Search050201 search, Pageable pageable);

    Tb504Form findById(Integer seq);

    Integer delete(Integer seq);

    List<List<String>> findAllByExcel(Search050201 search);
}
