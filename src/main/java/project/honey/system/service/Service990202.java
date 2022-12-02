package project.honey.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.system.dto.Tb902Dto;
import project.honey.system.dto.Tb905Dto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface Service990202 {

    Integer insert(Tb905Dto dto);

    Integer update(Tb905Dto dto);

    // 리스트 조회
    Page<Tb905Dto> findAll(Pageable pageable);

    Tb905Dto findById(Integer id);

    Integer delete(Integer seq);

    List<List<String>> findAllByExcel();
}
