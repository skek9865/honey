package project.honey.pay.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.pay.dto.*;

import java.util.List;

public interface Service030103 {

    Integer insert(Tb303Dto dto);

    Integer update(Tb303Dto dto);

    List<Tb302PopupDto> findAll(String empNo);

    Page<Tb303HomeDto> findAllByLeave(Pageable pageable,String payDt, String empNm, String postCd, String deptCd);

    Tb303Dto findById(Integer seq);

    Integer delete(Integer seq);

    String pitemeSaveOne(String empNo, String payDt, String rPayDt);

    String pitemeDelOne(String empNo, String payDt);

    String pitemeSave(String payDt, String rPayDt);

    String pitemeDel(String payDt);

    String payloadOne(String empNo, String payDt, String rPayDt);

    String payload(String payDt, String rPayDt);

}
