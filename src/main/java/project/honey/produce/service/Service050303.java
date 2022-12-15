package project.honey.produce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Dto050303;
import project.honey.produce.dto.search.Search050302;

import java.util.List;

public interface Service050303 {

    Page<Dto050303> findAll(Search050302 search, Pageable pageable);

    List<List<String>> findAllByExcel(Search050302 search);
}
