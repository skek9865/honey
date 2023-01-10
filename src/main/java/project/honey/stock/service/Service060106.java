package project.honey.stock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.stock.dto.Dto060105;
import project.honey.stock.dto.Dto060106;
import project.honey.stock.dto.search.Search060102;
import project.honey.stock.dto.search.Search060103;

import java.util.List;

public interface Service060106 {

    Page<Dto060106> findAll(Search060103 search, Pageable pageable);

    List<List<String>> findAllByExcel(Search060103 search);
}
