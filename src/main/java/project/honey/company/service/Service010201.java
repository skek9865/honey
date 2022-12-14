package project.honey.company.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.company.dto.Tb102Dto;

import java.util.List;
import java.util.Map;


public interface Service010201 {
    //통장 등록
    Integer insert(Tb102Dto dto);

    //통장 수정
    Integer update(Tb102Dto dto);

    //모든 통장 가져오기
    Page<Tb102Dto> findAll(Pageable pageable);

    //모든 통장 가져오기2
    List<Tb102Dto> findAll();

    //모든 통장 <id값, 계좌번호> 형식으로 가져오기
    Map<Integer, String> bank();

    //id값을 이용해 통장 가져오기
    Tb102Dto findById(Integer id);

    //id값을 이용해 통장 삭제
    Integer delete(Integer id);

    //excel 출력
    List<List<String>> findAllByExcel();
}
