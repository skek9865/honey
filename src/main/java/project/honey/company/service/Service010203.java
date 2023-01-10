package project.honey.company.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.company.dto.Tb104Dto;

import java.util.List;

public interface Service010203 {
    //카드 등록
    Integer insert(Tb104Dto dto);

    //카드 수정
    Integer update(Tb104Dto dto);

    //모든 카드 가져오기
    Page<Tb104Dto> findAll(Pageable pageable);

    //id값을 이용해 카드 가져오기
    Tb104Dto findById(Integer id);

    //id값을 이용해 카드 삭제
    Integer delete(Integer id);

    //카드한도 total 가져오기
    Integer getTotalLimitamt();

    //엑셀 출력
    List<List<String>> findAllByExcel();
}
