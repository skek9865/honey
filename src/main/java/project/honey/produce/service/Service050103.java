package project.honey.produce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.Tb405Dto;
import project.honey.business.dto.search.Search405;
import project.honey.business.dto.search.SearchPopUp405;
import project.honey.produce.dto.Tb502Dto;
import project.honey.produce.dto.Tb503Dto;
import project.honey.produce.dto.input.Tb503Input;

import java.util.List;

public interface Service050103 {

    Integer insert(Tb503Input dto);

    Integer update(Tb503Input dto);

    Page<Tb503Dto> findAll(Search405 search405, Pageable pageable);

    Tb503Input findById(Integer seq, String goodsCd);

    Integer delete(Integer seq);

    List<List<String>> findAllByExcel(Search405 search);
}
