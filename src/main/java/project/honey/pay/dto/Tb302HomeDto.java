package project.honey.pay.dto;

import lombok.*;
import project.honey.personDepart.entity.Tb201;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tb302HomeDto {

    private Integer seq;

    private String empNo;

    private String empNm;

    private String empDt;

    private String post;

    private String deptNm;

    private Integer payout; //지급액
    private Integer taxAmt; //과세액
    private Integer deduction;  //공제액
    private Integer actualPayment;  //실지급액

    //Dto 변환 메서드
    public static Tb302HomeDto of(Tb201 entity, Integer payout, Integer taxAmt, Integer deduction, Integer actualPayment) {

        return Tb302HomeDto.builder()
                .seq(entity.getSeq())
                .empNo(entity.getEmpNo())
                .empNm(entity.getEmpNm())
                .empDt(entity.getEmpDt())
                .post(entity.getPost())
                .deptNm(entity.getDeptCd())
                .payout(payout)
                .taxAmt(taxAmt)
                .deduction(deduction)
                .actualPayment(actualPayment)
                .build();
    }
}
