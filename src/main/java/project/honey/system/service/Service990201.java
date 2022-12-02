package project.honey.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.system.dto.Tb903Dto;
import project.honey.system.dto.Tb904Dto;

import java.util.LinkedHashMap;
import java.util.List;

public interface Service990201 {

    Integer insert(Tb904Dto dto);

    Integer update(Tb904Dto dto);

    Page<Tb904Dto> findAll(Pageable pageable);

    Tb904Dto findById(Integer seq);

    Integer delete(Integer seq);

    List<List<String>> findAllByExcel();

    LinkedHashMap<Integer, String> findThdMenuAll();
}
