package project.honey.business.dto.analaze;

import lombok.*;
import project.honey.business.entity.manage.Tb410;
import project.honey.business.entity.manage.Tb410_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Dto040302 {

    private Integer seq;
    private String estimno;
    private String custNm;
    private String empNm;
    private String expDt;
    private String payCondit;
    private String note;
    private String status;
    private String goodsNm;
    private String standard;
    private Integer qty;
    private Integer price;
    private Integer amt;
    private Integer vat;

    public static Dto040302 of(Tb410 entity, Tb410_1 tb410_1, String custNm, String empNm,String status, String goodsNm){
        String estimno = entity.getEstimDt().substring(0,4) + "-" + entity.getEstimDt().substring(4,6) + "-" + entity.getEstimDt().substring(6,8) + "-" + entity.getEstimNo();

        return Dto040302.builder()
                .seq(entity.getSeq())
                .estimno(estimno)
                .custNm(custNm)
                .empNm(empNm)
                .expDt(entity.getExpDt())
                .payCondit(entity.getPayCondit())
                .note(entity.getNote())
                .status(status)
                .goodsNm(goodsNm)
                .standard(tb410_1.getStandard())
                .qty(tb410_1.getQty())
                .price(tb410_1.getPrice())
                .amt(tb410_1.getAmt())
                .vat(tb410_1.getVat())
                .build();
    }
}
