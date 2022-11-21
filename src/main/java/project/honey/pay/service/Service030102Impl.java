package project.honey.pay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.pay.dto.Tb301Dto;
import project.honey.pay.dto.Tb302Dto;
import project.honey.pay.dto.Tb302ResultDto;
import project.honey.pay.entity.Tb302;
import project.honey.pay.repository.Tb302Repository;
import project.honey.pay.repository.Tb302RepositoryDsl;
import project.honey.personDepart.entity.Tb201;
import project.honey.personDepart.repository.Tb201Repository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service030102Impl implements Service030102{

    private final Tb201Repository tb201Repository;
    private final Tb302Repository tb302Repository;

    @Override
    public Integer insert(Tb301Dto dto) {
        return null;
    }

    @Override
    public Integer update(Tb301Dto dto) {
        return null;
    }

    // 개인별기준급여 리스트 조회
    @Override
    public Page<Tb302ResultDto> findAllByLeave(Pageable pageable, String empNm, String postCd, String deptCd) {
        Page<Tb201> result = tb201Repository.findAllByDsl(empNm, postCd, deptCd, pageable);
        List<Tb201> content = result.getContent();  // 사원 테이블의 내용

        List<Tb302ResultDto> dtos = new ArrayList<>();
        for (Tb201 tb201 : content) {               // 사원번호로 지급액 조회
            int payout = 0; //지급액
            int taxAmt = 0; //과세액
            int deduction = 0;  //공제액
            int actualPayment = 0;  //실지급액
            List<Tb302> list = tb302Repository.findAllByEmpNo(tb201.getEmpNo());
            for (Tb302 tb302 : list) {
                Double payAmt = tb302.getPayAmt();

                if(tb302.getItemDiv().equals("00001")){ // 지급일 떄
                   payout += payAmt;
                    if (tb302.getTaxDiv().equals("Y")) { // 지급이면서 과세일 떄
                        taxAmt += payAmt;
                    }
                } else if (tb302.getItemDiv().equals("00002")) {    // 공제일 때
                    deduction += payAmt;
                }

            }
            actualPayment = payout - deduction;
            dtos.add(Tb302ResultDto.of(tb201, payout, taxAmt, deduction, actualPayment));
        }

        return new PageImpl<>(dtos, pageable, result.getTotalElements());
    }

    @Override
    public Tb301Dto findById(Integer seq) {
        return null;
    }

    @Override
    public void delete(Integer seq) {

    }
}
