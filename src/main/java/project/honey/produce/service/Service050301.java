package project.honey.produce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Tb505Dto;
import project.honey.produce.dto.form.Tb505Form;
import project.honey.produce.dto.search.Search050301;

import java.util.List;

public interface Service050301 {

    Integer insert(Tb505Form dto);

    Integer update(Tb505Form dto);

    Page<Tb505Dto> findAll(Search050301 search, Pageable pageable);

    Tb505Form findById(Integer seq);

    Integer delete(Integer seq);

    List<List<String>> findAllByExcel(Search050301 search);
}
