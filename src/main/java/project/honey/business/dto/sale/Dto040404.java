package project.honey.business.dto.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb417;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Dto040404 {
    private Integer seq;
    private String outDt;
    private String custNm;
    private Integer price;
    private String note;

    public static Dto040404 of(Tb417 entity, String custNm){
        String outDt = entity.getAmountDt().substring(0,4) + "-" + entity.getAmountDt().substring(4,6) + "-" + entity.getAmountDt().substring(6,8) + "-" + entity.getAmountNo();

        return Dto040404.builder()
                .seq(entity.getSeq())
                .outDt(outDt)
                .custNm(custNm)
                .price(entity.getAmount())
                .note(entity.getNote())
                .build();
    }
}
