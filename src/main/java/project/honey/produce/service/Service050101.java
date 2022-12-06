package project.honey.produce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Tb501Dto;
import project.honey.system.dto.Tb903Dto;

import java.util.List;
import java.util.Map;

public interface Service050101 {

    Integer insert(Tb501Dto dto);

    Integer update(Tb501Dto dto);

    Page<Tb501Dto> findAll(Pageable pageable);

    Tb501Dto findById(Integer seq);

    Integer delete(Integer seq);

    List<List<String>> findAllByExcel();

    Map<String, String> findAllBySelect();
}
