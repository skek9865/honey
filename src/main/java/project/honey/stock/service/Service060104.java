package project.honey.stock.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.stock.dto.Dto060104;
import project.honey.stock.dto.search.Search060101;

import java.util.List;

public interface Service060104 {

    Page<Dto060104> findAll(Search060101 search, Pageable pageable);

    List<List<String>> findAllByExcel(Search060101 search);
}
