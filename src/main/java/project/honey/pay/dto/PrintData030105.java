package project.honey.pay.dto;

import lombok.*;
import org.springframework.util.StringUtils;
import project.honey.pay.entity.Tb301;
import project.honey.pay.entity.Tb303;
import project.honey.pay.repository.Tb301Repository;
import project.honey.personDepart.entity.Tb201;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class PrintData030105 {

    // 사원관련
    private String payDt;
    private String rPay;
    private String rPayDt;
    private String empNm;
    private String empNo;
    private String post;
    private String dept;

    // 급여관련
    private List<PrintData030105_1> payouts; //지급리스트
    private List<PrintData030105_1> deductions; // 공제 리스트

    // 총합관련
    private Integer totalPayout;
    private Integer totalTaxAmt;
    private Integer totalDeduction;
    private Integer totalActualPayment;

    public static PrintData030105 of(Tb201 tb201, List<Tb303> tb303s, Map<String, String> postMap, Map<String, String> deptMap,
                                     Map<String, String> itemMap, Tb301Repository repository){

        List<PrintData030105_1> payouts = new ArrayList<>();
        List<PrintData030105_1> deductions = new ArrayList<>();

        int payout = 0; //지급액
        int taxAmt = 0; //과세액
        double deductionSub = 0.0;
        int deduction = 0;  //공제액
        int actualPayment = 0;  //실지급액
        String payDt = "";
        String rPayDt = "";

        for (Tb303 tb303 : tb303s) {
            // 지급, 공제 추가
            if(tb303.getItemDiv().equals("00001")){
                PrintData030105_1 dto = PrintData030105_1.builder()
                        .itemNm(itemMap.get(tb303.getItemCd()))
                        .pay(tb303.getPayAmt().intValue())
                        .build();
                payouts.add(dto);
            }else{
                PrintData030105_1 dto = PrintData030105_1.builder()
                        .itemNm(itemMap.get(tb303.getItemCd()))
                        .pay(tb303.getPayAmt().intValue())
                        .taxRate(tb303.getTaxRate())
                        .build();
                deductions.add(dto);
            }

            // 계산
            try {
                Tb301 tb301 = repository.findByItemCd(tb303.getItemCd()).orElseThrow(RuntimeException::new);
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
                payDt = tb303.getPayDt();
                rPayDt = tb303.getRPayDt();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 지급액 - 과세아닌 금액 * 세제율
        deduction += (int) (Math.ceil((payout - (payout - taxAmt)) * deductionSub) / 100);
        actualPayment = payout - deduction;

        return PrintData030105.builder()
                .payDt(payDt.substring(0, 4) + "년 " + payDt.substring(4) + "월")
                .rPay(rPayDt.substring(4, 6) + "월 " + rPayDt.substring(6) + "일")
                .rPayDt(rPayDt.substring(0,4) + "년 " + rPayDt.substring(4, 6) + "월 " + rPayDt.substring(6) + "일")
                .empNm(tb201.getEmpNm())
                .empNo(tb201.getEmpNo())
                .post(postMap.get(tb201.getPost()))
                .dept(deptMap.get(tb201.getDeptCd()))
                .payouts(payouts)
                .deductions(deductions)
                .totalPayout(payout)
                .totalTaxAmt(taxAmt)
                .totalDeduction(deduction)
                .totalActualPayment(actualPayment)
                .build();
    }

}
