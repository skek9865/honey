package project.honey.stock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.stock.dto.Tb601Dto;
import project.honey.stock.dto.Tb602Dto;
import project.honey.stock.dto.form.Tb601Form;
import project.honey.stock.dto.form.Tb602Form;
import project.honey.stock.dto.search.Search060101;
import project.honey.stock.dto.search.Search060102;

import java.util.List;

public interface Service060102 {

    Page<Tb602Dto> findAll(Search060102 search, Pageable pageable);

    Tb602Form findById(Integer seq);

    Integer insert(Tb602Form dto);

    Integer update(Tb602Form dto);

    Integer delete(Integer seq);

    List<List<String>> findAllByExcel(Search060102 search);
}
