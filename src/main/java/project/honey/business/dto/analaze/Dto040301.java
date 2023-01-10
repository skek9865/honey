package project.honey.business.dto.analaze;

import lombok.*;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.entity.manage.Tb412_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Dto040301 {
    private Integer seq;
    private String saleNo;
    private String goodsNm;
    private String standard;
    private Integer qty;
    private Integer price;
    private Integer amt;
    private Integer vat;
    private String custNm;
    private String projectNm;
    private String note;
    private String note2;

    public static Dto040301 of(Tb412 entity, Tb412_1 tb412_1, String goodsNm, String custNm, String projectNm){
        String saleNo = entity.getSaleDt().substring(0,4) + "-" + entity.getSaleDt().substring(4,6) + "-" + entity.getSaleDt().substring(6,8) + "-" + entity.getSaleNo();

        return Dto040301.builder()
                .seq(entity.getSeq())
                .saleNo(saleNo)
                .goodsNm(goodsNm)
                .standard(tb412_1.getStandard())
                .qty(tb412_1.getQty())
                .price(tb412_1.getPrice())
                .amt(tb412_1.getAmt())
                .vat(tb412_1.getVat())
                .custNm(custNm)
                .projectNm(projectNm)
                .note(entity.getNote())
                .note2(entity.getNote2())
                .build();
    }
}
