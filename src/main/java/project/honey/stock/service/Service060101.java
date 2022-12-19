package project.honey.stock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Tb505Dto;
import project.honey.produce.dto.form.Tb505Form;
import project.honey.produce.dto.search.Search050301;
import project.honey.stock.dto.Tb601Dto;
import project.honey.stock.dto.form.Tb601Form;
import project.honey.stock.dto.search.Search060101;

import java.util.List;

public interface Service060101 {

    Page<Tb601Dto> findAll(Search060101 search, Pageable pageable);

    Tb601Form findById(Integer seq);

    Integer insert(Tb601Form dto);

    Integer update(Tb601Form dto);

    Integer delete(Integer seq);

    List<List<String>> findAllByExcel(Search060101 search);
}
