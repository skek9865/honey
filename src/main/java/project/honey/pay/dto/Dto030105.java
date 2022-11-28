package project.honey.pay.dto;

import lombok.*;
import project.honey.personDepart.entity.Tb201;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Dto030105 {

    private String empDt;
    private String empNo;
    private String empNm;
    private String post;
    private Integer payout;
    private Integer deduction;
    private Integer actualPayment;

    public static Dto030105 of(Tb201 tb201, int payout, int deduction, int actualPayment) {
        return Dto030105.builder()
                .empDt(tb201.getEmpDt())
                .empNo(tb201.getEmpNo())
                .empNm(tb201.getEmpNm())
                .post(tb201.getPost())
                .payout(payout)
                .deduction(deduction)
                .actualPayment(actualPayment)
                .build();
    }
}
