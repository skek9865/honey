package project.honey.company.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.company.dto.Tb105Dto;
import project.honey.company.dto.Tb105_1Dto;

import java.util.List;
import java.util.Map;

public interface Service010204 {
    //대출 등록
    Integer insert(Tb105Dto dto);

    //대출 수정
    Integer update(Tb105Dto dto);

    //모든 대출 정보 가져오기
    Page<Tb105Dto> findAll(Pageable pageable);

    //id값을 이용해 대출정보 가져오기
    Tb105Dto findById(Integer id);

    //id값을 이용해 대출 삭제
    Integer delete(Integer id);

    //약정한도 total 가져오기
    Integer getTotalLimitamt(List<Tb105Dto> dtoList);

    //납입금 total 가져오기
    Integer getTotalInstamt(List<Tb105Dto> dtoList);

    //id값을 이용해 납입관리 list 가져오기
    List<Tb105_1Dto> findAllListById(Integer id);

    //엑셀 출력
    List<List<String>> findAllByExcel();

}
