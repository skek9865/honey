package project.honey.pay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import project.honey.pay.dto.*;
import project.honey.pay.entity.Tb301;
import project.honey.pay.entity.Tb302;
import project.honey.pay.entity.Tb303;
import project.honey.pay.repository.Tb301Repository;
import project.honey.pay.repository.Tb302Repository;
import project.honey.pay.repository.Tb303Repository;
import project.honey.personDepart.entity.Tb201;
import project.honey.personDepart.repository.Tb201Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service030103Impl implements Service030103{

    private final Tb201Repository tb201Repository;
    private final Tb301Repository tb301Repository;
    private final Tb302Repository tb302Repository;
    private final Tb303Repository tb303Repository;

    @Override
    public Integer insert(Tb303Dto dto) {
        return null;
    }

    @Override
    public Integer update(Tb303Dto dto) {
        return null;
    }

    @Override
    public List<Tb302PopupDto> findAll(String empNo) {
        return null;
    }

    @Override
    public Page<Tb303HomeDto> findAllByLeave(Pageable pageable,String payDt, String empNm, String postCd, String deptCd) {

        Page<Tb201> result = tb201Repository.findAllByLeave(empNm, postCd, deptCd, pageable);
        List<Tb201> content = result.getContent();  // 사원 테이블의 내용

        List<Tb303HomeDto> dtos = new ArrayList<>();
        for (Tb201 tb201 : content) {               // 사원번호로 지급액 조회
            int payout = 0; //지급액
            int taxAmt = 0; //과세액
            double deductionSub = 0.0;
            int deduction = 0;  //공제액
            int actualPayment = 0;  //실지급액
            String pay = "";      //급여년월
            String rPay = "";     //실지급일

            List<Tb303> list = tb303Repository.findAllByEmpNoAndPayDt(tb201.getEmpNo(), payDt);
            // 성능 안나오면 QueryDsl로 변경
            for (Tb303 tb303 : list) {
                log.info("tb303 = {}", tb303);
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
                    pay = tb303.getPayDt();
                    rPay = tb303.getRPayDt();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 지급액 - 과세아닌 금액 * 세제율
            deduction += (int) (Math.ceil((payout - (payout - taxAmt)) * deductionSub) / 100);
            actualPayment = payout - deduction;
            dtos.add(Tb303HomeDto.of(tb201, pay, rPay, payout, taxAmt, deduction, actualPayment));
        }

        // 날짜 변환 로직
        List<Tb303HomeDto> collect = dtos.stream().peek(dto -> {
            String empDt = dto.getEmpDt();
            String pay = dto.getPayDt();
            String rPay = dto.getRPayDt();
            dto.setEmpDt(empDt.substring(0, 4) + "-" + empDt.substring(4, 6) + "-" + empDt.substring(6));
            if (StringUtils.hasText(pay))dto.setPayDt(pay.substring(0, 4) + "-" + pay.substring(4));
            if(StringUtils.hasText(rPay))dto.setRPayDt(rPay.substring(0, 4) + "-" + rPay.substring(4, 6) + "-" + rPay.substring(6));
        }).collect(Collectors.toList());

        return new PageImpl<>(collect, pageable, result.getTotalElements());
    }

    @Override
    public Tb303Dto findById(Integer seq) {
        return null;
    }

    @Override
    public Integer delete(Integer seq) {
        return null;
    }

    @Override
    @Transactional
    public String pitemeSaveOne(String empNo, String payDt, String rPayDt) {

        payDt = payDt.replaceAll("-", "");
        rPayDt = rPayDt.replaceAll("-", "");

        tb303Repository.deleteAllByEmpNoAndPayDt(empNo, payDt);

        log.info("empNo : {}", empNo);
        createTb303AndSave(empNo, payDt, rPayDt);
        return empNo;
    }

    @Override
    @Transactional
    public String pitemeDel(String payDt) {
        payDt = payDt.replaceAll("-", "");
        tb303Repository.deleteAllByPayDt(payDt);
        return "ok";
    }

    @Override
    @Transactional
    public String pitemeDelOne(String empNo, String payDt) {
        payDt = payDt.replaceAll("-", "");
        tb303Repository.deleteAllByEmpNoAndPayDt(empNo, payDt);
        return empNo;
    }

    @Override
    @Transactional
    public String pitemeSave(String payDt, String rPayDt) {
        tb201Repository.findAll()
                .forEach(entity -> pitemeSaveOne(entity.getEmpNo(), payDt, rPayDt));

        return "ok";
    }

    // 급여계산 로직
    private void createTb303AndSave(String empNo, String payDt, String rPayDt) {

        List<Tb302> tb302s = tb302Repository.findAllByEmpNoOrderByItemCdAsc(empNo);

        int price = 0;
        for (Tb302 tb302 : tb302s) {
            if (tb302.getItemDiv().equals("00001") && tb302.getTaxDiv().equals("Y")) {
                price += tb302.getPayAmt();
            }
        }

        for (Tb302 tb302 : tb302s) {
            Tb301 tb301 = tb301Repository.findByItemCd(tb302.getItemCd()).orElseThrow(RuntimeException::new);
            Double payAmt = 0.0;
            if (tb302.getItemDiv().equals("00001")) {
                payAmt = tb302.getPayAmt();
            }else{
                if (price == 0) {
                    payAmt = 0.0;
                } else {
                    if (tb301.getTaxRate() > 0.0) {
                        payAmt = Math.floor(price * tb302.getPayAmt() / 100);
                    }else {
                        payAmt = tb302.getPayAmt();
                    }
                }
            }

            Tb303 tb303 = Tb303.builder()
                    .payDt(payDt)
                    .empNo(empNo)
                    .itemDiv(tb302.getItemDiv())
                    .itemCd(tb302.getItemCd())
                    .taxRate(tb302.getPayAmt())
                    .payAmt(payAmt)
                    .rPayDt(rPayDt)
                    .build();
            tb303Repository.save(tb303);
        }
    }



    @Override
    @Transactional
    public String payload(String payDt, String rPayDt) {
        tb201Repository.findAll()
                .forEach(entity -> payloadOne(entity.getEmpNo(), payDt, rPayDt));

        return "ok";
    }

    @Override
    @Transactional
    public String payloadOne(String empNo, String payDt, String rPayDt) {
        String[] split = payDt.split("-");
        int year = Integer.parseInt(split[0]);
        int month = Integer.parseInt(split[1]);
        if (month == 1) {
            month = 12;
            year = year - 1;
        }else{
            month = month - 1;
        }

        rPayDt = rPayDt.replaceAll("-", "");
        payDt = payDt.replaceAll("-", "");

        // 이전달
        String prevPayDt = year + String.valueOf(month);

        List<Tb303> tb303s = tb303Repository.findAll().stream()
                .filter(f -> f.getEmpNo().equals(empNo) && f.getPayDt().equals(prevPayDt))
                .collect(Collectors.toList());

        // 이전 데이터 삭제
        tb303Repository.deleteAllByEmpNoAndPayDt(empNo, payDt);

        for (Tb303 tb303 : tb303s) {
            Tb303 entity = Tb303.builder()
                    .payDt(payDt)
                    .empNo(empNo)
                    .itemDiv(tb303.getItemDiv())
                    .itemCd(tb303.getItemCd())
                    .taxRate(tb303.getTaxRate())
                    .payAmt(tb303.getPayAmt())
                    .rPayDt(rPayDt)
                    .build();

            tb303Repository.save(entity);
        }
        return empNo;
    }


}
