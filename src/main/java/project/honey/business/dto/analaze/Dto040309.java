package project.honey.business.dto.analaze;

import lombok.*;
import project.honey.business.entity.manage.Tb412;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Dto040309 {
    private Integer seq;
    private String saleNo;
    private String custNm;
    private Integer amt;
    private Integer vat;
    private Integer tot;

    public static Dto040309 of(Tb412 entity, String custNm, Integer amt, Integer vat, Integer tot){
        String saleNo = entity.getSaleDt().substring(0,4) + "-" + entity.getSaleDt().substring(4,6) + "-" + entity.getSaleDt().substring(6,8) + "-" + entity.getSaleNo();

        return Dto040309.builder()
                .seq(entity.getSeq())
                .saleNo(saleNo)
                .custNm(custNm)
                .amt(amt)
                .vat(vat)
                .tot(tot)
                .build();

    }
}
