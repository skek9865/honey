package project.honey.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.system.dto.Tb901Dto;

public interface Service990101 {

    // 사용자 등록
    String insert(Tb901Dto dto);

    // 사용자 수정
    String update(Tb901Dto dto);

    // 사용자 리스트 조회
    Page<Tb901Dto> findAll(Pageable pageable);

    // 사용자 단건 조회
    Tb901Dto findById(String userId);

    // 사용자 삭제
    void delete(String userId);
}
