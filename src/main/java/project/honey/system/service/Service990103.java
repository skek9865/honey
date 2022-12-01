package project.honey.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.system.dto.Tb901Dto;
import project.honey.system.dto.Tb903Dto;
import project.honey.system.entity.Tb903;

import java.util.List;

public interface Service990103 {

    Integer insert(Tb903Dto dto);

    Integer update(Tb903Dto dto);

    Page<Tb903Dto> findAll(Pageable pageable);

    Tb903Dto findById(Integer seq);

    Integer delete(Integer seq);

    List<List<String>> findAllByExcel();
}
