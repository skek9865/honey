package project.honey.business.service.basic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.basic.Tb402Dto;
import project.honey.business.dto.basic.Tb402Popup;
import project.honey.business.form.basic.Tb402Form;

import java.util.List;

public interface Service040102 {

    Boolean insert(Tb402Form form);

    Page<Tb402Dto> findAllByDsl(String custNm, String ceoNm, String empCd, String class1, Pageable pageable);

    List<List<String>> findAllByExcel(String custNm, String ceoNm, String empCd, String class1);

    Tb402Dto findById(Integer id);

    Boolean update(Tb402Form form);

    Boolean delete(Integer id);

    List<Tb402Popup> findAllByPopup(String custNm);
}
