package project.honey.stock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.stock.dto.Tb601Dto;
import project.honey.stock.dto.Tb603Dto;
import project.honey.stock.dto.form.Tb601Form;
import project.honey.stock.dto.form.Tb603Form;
import project.honey.stock.dto.search.Search060101;
import project.honey.stock.dto.search.Search060103;

import java.util.List;

public interface Service060103 {

    Page<Tb603Dto> findAll(Search060103 search, Pageable pageable);

    Tb603Form findById(Integer seq);

    Integer insert(Tb603Form dto);

    Integer update(Tb603Form dto);

    Integer delete(Integer seq);

    List<List<String>> findAllByExcel(Search060103 search);
}
