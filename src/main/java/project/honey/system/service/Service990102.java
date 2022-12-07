package project.honey.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.system.dto.Tb902Dto;
import project.honey.system.dto.Tb903Dto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface Service990102 {

    Integer insert(Tb902Dto dto);

    Integer update(Tb902Dto dto);

    // 리스트 조회
    Page<Tb902Dto> findAll(Pageable pageable);

    // 로그인시 이력 저장
    void insertHistory(HttpServletRequest request, String userId);

    Tb902Dto findById(Integer id);

    Integer delete(Integer seq);

    List<List<String>> findAllByExcel();
}
