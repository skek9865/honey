package project.honey.pay.dto;

import lombok.*;
import project.honey.pay.entity.Tb303;
import project.honey.personDepart.entity.Tb201;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class PayrollDto {

    private String empDt;
    private String empNm;
    private String post;
    private List<Integer> pays = new ArrayList<>();
    private Integer payout;
    private Integer deduction;
    private Integer actualPayment;

    public static PayrollDto of(Tb201 tb201, List<Integer> pays, int payout, int deduction, int actualPayment) {
        Collections.reverse(pays);
        return PayrollDto.builder()
                .empDt(tb201.getEmpDt())
                .empNm(tb201.getEmpNm())
                .post(tb201.getPost())
                .pays(pays)
                .payout(payout)
                .deduction(deduction)
                .actualPayment(actualPayment)
                .build();
    }
}
