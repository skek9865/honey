package project.honey.stock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.stock.dto.Dto060104;
import project.honey.stock.dto.Dto060105;
import project.honey.stock.dto.search.Search060101;
import project.honey.stock.dto.search.Search060102;

import java.util.List;

public interface Service060105 {

    Page<Dto060105> findAll(Search060102 search, Pageable pageable);

    List<List<String>> findAllByExcel(Search060102 search);
}
