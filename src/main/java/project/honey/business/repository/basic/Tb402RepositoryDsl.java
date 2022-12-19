package project.honey.business.repository.basic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.honey.business.dto.basic.Tb402Popup;
import project.honey.business.entity.basic.Tb402;

import java.util.List;

public interface Tb402RepositoryDsl {

    Page<Tb402> findAllByDsl(String custNm, String ceoNm, String empCd, String class1, Pageable pageable);
    List<Tb402> findAllByExcel(String custNm, String ceoNm, String empCd, String class1);

    List<Tb402Popup> findAllByPopup(String custNm);
}
