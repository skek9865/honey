package project.honey.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.system.dto.CodeDto;
import project.honey.system.dto.Tb906Dto;

import java.util.List;

public interface Service990301 {

    Integer insert(Tb906Dto dto);

    Integer update(Tb906Dto dto);

    Page<Tb906Dto> findAll(Pageable pageable, String fstId);

    Tb906Dto findById(Integer seq);

    void delete(Integer seq);

    List<CodeDto> findByFstId(String fstId);
}
