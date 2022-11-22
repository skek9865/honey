package project.honey.pay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.pay.dto.Tb301Dto;
import project.honey.pay.dto.Tb302PopupDto;
import project.honey.pay.dto.Tb302HomeDto;
import project.honey.pay.entity.Tb302;
import project.honey.pay.repository.Tb302Repository;
import project.honey.personDepart.entity.Tb201;
import project.honey.personDepart.repository.Tb201Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<Tb302PopupDto> findAll(String empNo) {
        Tb201 tb201 = tb201Repository.findByEmpNo(empNo).orElseThrow(RuntimeException::new);
        List<Tb302> list = tb302Repository.findAllByEmpNo(empNo);

        log.info("list.size() = {}", list.size());

        List<Tb302PopupDto> dtos = new ArrayList<>();

        for (Tb302 tb302 : list) {
            Tb302PopupDto dto = Tb302PopupDto.builder()
                    .seq(tb302.getSeq())
                    .empNo(tb302.getEmpNo())
                    .empNm(tb201.getEmpNm())
                    .post(tb201.getPost())
                    .itemDiv(tb302.getItemDiv())
                    .taxDiv(tb302.getTaxDiv())
                    .itemCd(tb302.getItemCd())
                    .payAmt(tb302.getPayAmt())
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }

    // 개인별기준급여 리스트 조회
    @Override
    public Page<Tb302HomeDto> findAllByLeave(Pageable pageable, String empNm, String postCd, String deptCd) {
        Page<Tb201> result = tb201Repository.findAllByLeave(empNm, postCd, deptCd, pageable);
        log.info("result : {}", result.getContent());
        List<Tb201> content = result.getContent();  // 사원 테이블의 내용

        List<Tb302HomeDto> dtos = new ArrayList<>();
        for (Tb201 tb201 : content) {               // 사원번호로 지급액 조회
            int payout = 0; //지급액
            int taxAmt = 0; //과세액
            double deductionSub = 0.0;
            int deduction = 0;  //공제액
            int actualPayment = 0;  //실지급액
            List<Tb302> list = tb302Repository.findAllByEmpNo(tb201.getEmpNo());
            for (Tb302 tb302 : list) {
                log.info("tb302 : {}", tb302 );
                Double payAmt =tb302.getPayAmt();

                if(tb302.getItemDiv().equals("00001")){ // 지급일 떄
                   payout += payAmt;
                    if (tb302.getTaxDiv().equals("Y")) { // 지급이면서 과세일 떄
                        taxAmt += payAmt;
                    }
                } else if (tb302.getItemDiv().equals("00002")) {    // 공제일 때
                    log.info("payAmt = " + payAmt);
                    deductionSub += payAmt;
                }
            }
            deduction = (int) (Math.ceil(payout * deductionSub)/100);
            actualPayment = payout - deduction;
            dtos.add(Tb302HomeDto.of(tb201, payout, taxAmt, deduction, actualPayment));
        }


        List<Tb302HomeDto> collect = dtos.stream().map(dto -> {
            String empDt = dto.getEmpDt();
            dto.setEmpDt(empDt.substring(0, 4) + "-" + empDt.substring(4, 6) + "-" + empDt.substring(6));
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(collect, pageable, result.getTotalElements());
    }

    @Override
    public Tb301Dto findById(Integer seq) {
        return null;
    }

    @Override
    public Integer delete(Integer seq) {
        return null;
    }
}
