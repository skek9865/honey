package project.honey.pay.repository;

import project.honey.pay.entity.Tb303;

import java.util.List;

public interface Tb303RepositoryDsl {

    // Tb303에서 사용여부가 Y인 데이터만 조회
    List<Tb303> findAllByUsePay(String empNo, String payDt);
}
