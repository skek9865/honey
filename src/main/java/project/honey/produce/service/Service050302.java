package project.honey.produce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Tb505Dto;
import project.honey.produce.dto.Tb506Dto;
import project.honey.produce.dto.form.Tb505Form;
import project.honey.produce.dto.form.Tb506Form;
import project.honey.produce.dto.search.Search050301;
import project.honey.produce.dto.search.Search050302;

import java.util.List;

public interface Service050302 {

    Integer insert(Tb506Form dto);

    Integer update(Tb506Form dto);

    Page<Tb506Dto> findAll(Search050302 search, Pageable pageable);

    Tb506Form findById(Integer seq);

    Integer delete(Integer seq);

    List<List<String>> findAllByExcel(Search050302 search);
}
