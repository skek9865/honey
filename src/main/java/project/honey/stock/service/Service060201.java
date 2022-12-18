package project.honey.stock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.stock.dto.Dto060111;
import project.honey.stock.dto.Dto060201;
import project.honey.stock.dto.search.Search060111;
import project.honey.stock.dto.search.Search060201;

import java.util.List;

public interface Service060201 {

    Page<Dto060201> findAll(Search060201 search, Pageable pageable);

    List<List<String>> findAllByExcel(Search060201 search);
}
