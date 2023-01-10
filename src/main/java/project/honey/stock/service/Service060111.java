package project.honey.stock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.stock.dto.Dto060109;
import project.honey.stock.dto.Dto060111;
import project.honey.stock.dto.search.Search060109;
import project.honey.stock.dto.search.Search060111;

import java.util.List;

public interface Service060111 {

    Page<Dto060111> findAll(Search060111 search, Pageable pageable);

    List<List<String>> findAllByExcel(Search060111 search);
}
