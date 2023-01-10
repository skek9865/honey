package project.honey.stock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.stock.dto.Tb604Dto;
import project.honey.stock.dto.form.Tb604Form;
import project.honey.stock.dto.search.Search060110;

import java.util.List;

public interface Service060110 {

    Page<Tb604Dto> findAll(Search060110 search, Pageable pageable);

    Tb604Form findById(Integer seq);

    Integer insert(Tb604Form dto);

    Integer update(Tb604Form dto);

    Integer delete(Integer seq);

    List<List<String>> findAllByExcel(Search060110 search);
}
