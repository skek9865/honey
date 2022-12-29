package project.honey.business.dto.analaze;

import lombok.*;
import project.honey.business.entity.sale.Tb417;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Dto040308 {

    private Integer seq;
    private String amountNo;
    private String custNm;
    private Integer price;
    private String note;

    public static Dto040308 of(Tb417 entity, String custNm){
        String amountNo = entity.getAmountDt().substring(0,4) + "-" + entity.getAmountDt().substring(4,6) + "-" + entity.getAmountDt().substring(6,8) + "-" + entity.getAmountNo();

        return Dto040308.builder()
                .seq(entity.getSeq())
                .amountNo(amountNo)
                .custNm(custNm)
                .price(entity.getAmount())
                .note(entity.getNote())
                .build();
    }
}
