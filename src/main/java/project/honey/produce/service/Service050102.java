package project.honey.produce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.produce.dto.Tb501Dto;
import project.honey.produce.dto.Tb502Dto;

import java.util.List;

public interface Service050102 {

    Integer insert(Tb502Dto dto);

    Integer update(Tb502Dto dto);

    Page<Tb502Dto> findAll(Pageable pageable);

    Tb502Dto findById(Integer seq);

    Integer delete(Integer seq);

    List<List<String>> findAllByExcel();
}
