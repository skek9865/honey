package project.honey.business.dto.analaze;

import lombok.*;
import project.honey.business.entity.manage.Tb412;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Dto040307 {
    private Integer seq;
    private String empNm;
    private String custCd;
    private String custNm;
    private Integer salePrice;
    private Integer getPrice;
    private Integer totPrice;

    public static Dto040307 of(Tb412 entity, String empNm, String custNm,
                               Integer salePrice, Integer getPrice, Integer totPrice){
        return Dto040307.builder()
                .seq(entity.getSeq())
                .empNm(empNm)
                .custCd(entity.getCustCd())
                .custNm(custNm)
                .salePrice(salePrice)
                .getPrice(getPrice)
                .totPrice(totPrice)
                .build();
    }
}
