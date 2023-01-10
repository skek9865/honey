package project.honey.business.dto.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb416;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Dto040403 {
    private Integer seq;
    private String empNm;
    private String custNm;
    private Integer getPrice;
    private Integer outPrice;
    private Integer totPrice;

    public static Dto040403 of(Tb416 entity, String empNm, String custNm, Integer getPrice,
                               Integer outPrice, Integer totPrice){
        return Dto040403.builder()
                .seq(entity.getSeq())
                .empNm(empNm)
                .custNm(custNm)
                .getPrice(getPrice)
                .outPrice(outPrice)
                .totPrice(totPrice)
                .build();
    }
}
