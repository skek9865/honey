package project.honey.company.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.company.dto.Tb103Dto;
import project.honey.company.entity.Tb103;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface Service010202 {
    //인증서 등록
    Integer insert(Tb103Dto dto);

    //인증서 수정
    Integer update(Tb103Dto dto);

    //모든 인증서 가져오기
    Page<Tb103Dto> findAll(Pageable pageable);

    //모든 인증서 가져오기2
    List<Tb103Dto> findAll();

    //모든 인증서 <id값, 이름> 형식으로 가져오기
    Map<Integer, String> cer();

    //id값을 이용해 인증서 가져오기
    Tb103Dto findById(Integer id);

    //id값을 이용해 인증서 삭제
    Integer delete(Integer id);

}
