package project.honey.business.dto.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb416;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Dto040405 {
    private Integer seq;
    private String buyNo;
    private String custNm;
    private Integer amt;
    private Integer vat;
    private Integer tot;

    public static Dto040405 of(Tb416 entity, String custNm, Integer amt, Integer vat){
        Integer tot = amt + vat;
        String buyNo = entity.getBuyDt().substring(0,4) + "-" + entity.getBuyDt().substring(4,6) + "-" + entity.getBuyDt().substring(6,8) + "-" + entity.getBuyNo();

        return Dto040405.builder()
                .seq(entity.getSeq())
                .buyNo(buyNo)
                .custNm(custNm)
                .amt(amt)
                .vat(vat)
                .tot(tot)
                .build();
    }
}
