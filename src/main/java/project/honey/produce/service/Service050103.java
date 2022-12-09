package project.honey.produce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.search.Search405;
import project.honey.produce.dto.Tb503Dto;
import project.honey.produce.dto.form.Tb503Form;

import java.util.List;

public interface Service050103 {

    Integer insert(Tb503Form dto);

    Integer update(Tb503Form dto);

    Page<Tb503Dto> findAll(Search405 search405, Pageable pageable);

    Tb503Form findById(Integer seq, String goodsCd);

    Integer delete(Integer seq);

    List<List<String>> findAllByExcel(Search405 search);
}
