package project.honey.stock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Dto050104;
import project.honey.stock.dto.Dto060107;
import project.honey.stock.dto.Tb603_1Dto;
import project.honey.stock.dto.search.Search060107;

import java.util.List;

public interface Service060107 {

    Page<Dto060107> findAll(Search060107 search, Pageable pageable);

    List<List<String>> findAllByExcel(Search060107 search);
}
