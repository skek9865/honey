package project.honey.pay.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.pay.dto.Tb301Dto;
import project.honey.system.dto.CodeDto;

import java.util.List;

public interface Service030101 {

    Integer insert(Tb301Dto dto);

    Integer update(Tb301Dto dto);

    Page<Tb301Dto> findAll(Pageable pageable);

    Tb301Dto findById(Integer seq);

    Integer delete(Integer seq);

    List<CodeDto> findAllItem();

    List<String> findAllByUseItemNm();

    List<List<String>> findAllByExcel();
}
