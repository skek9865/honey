package project.honey.stock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.stock.dto.Dto060108;
import project.honey.stock.dto.Dto060109;
import project.honey.stock.dto.search.Search060103;
import project.honey.stock.dto.search.Search060109;

import java.util.List;

public interface Service060109 {

    Page<Dto060109> findAll(Search060109 search, Pageable pageable);

    List<List<String>> findAllByExcel(Search060109 search);
}
