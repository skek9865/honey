package project.honey.produce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Tb504Dto;
import project.honey.produce.dto.form.Tb504_1Form;
import project.honey.produce.dto.search.Search050201;

public interface Service050202 {
    Integer insert(Tb504_1Form dto);

    Integer update(Tb504_1Form dto);

    Page<Tb504Dto> findAll(Search050201 search, Pageable pageable);

    Tb504_1Form findById(Integer seq);

    Integer delete(Integer seq);
}
