package project.honey.produce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Tb504Dto;
import project.honey.produce.dto.form.Tb504Form;
import project.honey.produce.dto.search.Search050201;

public interface Service050201 {

    Page<Tb504Dto> findAll(Search050201 search, Pageable pageable);

    Tb504Form findById(Integer seq);
}
