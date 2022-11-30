package project.honey.pay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.pay.dto.Dto030105;
import project.honey.pay.dto.PayrollDto;
import project.honey.pay.entity.Tb301;
import project.honey.pay.entity.Tb303;
import project.honey.pay.repository.Tb301Repository;
import project.honey.pay.repository.Tb303Repository;
import project.honey.personDepart.entity.Tb201;
import project.honey.personDepart.repository.Tb201Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service030105Impl implements Service030105 {

    private final Tb201Repository tb201Repository;
    private final Tb301Repository tb301Repository;
    private final Tb303Repository tb303Repository;

    @Override
    public Page<Dto030105> findAll(Pageable pageable, String payDt, String empNm, String postCd, String deptCd) {
        payDt = payDt.replaceAll("-", "");

        // 퇴사하지 않은 사원 조회
        Page<Tb201> tb201s = tb201Repository.findAllByLeave(empNm, postCd, deptCd, pageable);

        List<Dto030105> dtos = new ArrayList<>();
        for (Tb201 tb201 : tb201s) {
            // 사원 급여내역에서 해당 월의 사용중인 급여만 조회
            List<Tb303> tb303s = tb303Repository.findAllByUsePay(tb201.getEmpNo(), payDt);
            int payout = 0; //지급액
            int taxAmt = 0; //과세액
            double deductionSub = 0.0;
            int deduction = 0;  //공제액
            int actualPayment = 0;  //실지급액

            for (Tb303 tb303 : tb303s) {
                try {
                    Tb301 tb301 = tb301Repository.findByItemCd(tb303.getItemCd()).orElseThrow(RuntimeException::new);
                    Double payAmt = tb303.getPayAmt();
                    Double taxRate = tb303.getTaxRate();

                    if (tb303.getItemDiv().equals("00001")) { // 지급일 떄
                        payout += payAmt;
                        if (tb301.getTaxDiv().equals("Y")) { // 지급이면서 과세일 떄
                            taxAmt += payAmt;
                        }
                    } else if (tb303.getItemDiv().equals("00002")) {    // 공제일 때
                        if(tb301.getTaxRate() > 0) deductionSub += taxRate;
                        else deduction += payAmt;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 지급액 - 과세아닌 금액 * 세제율
            deduction += (int) (Math.ceil((payout - (payout - taxAmt)) * deductionSub) / 100);
            actualPayment = payout - deduction;

            dtos.add(Dto030105.of(tb201, payout, deduction, actualPayment));
        }

        return new PageImpl<>(dtos.stream().filter(f -> f.getPayout() > 0).collect(Collectors.toList()), pageable, tb201s.getTotalElements());
    }
}
