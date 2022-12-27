package project.honey.business.dto.analaze;

import lombok.*;
import project.honey.business.entity.manage.Tb411;
import project.honey.business.entity.manage.Tb411_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Dto040303 {

    private Integer seq;
    private String orderNo;
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
    private String deadDt;

    public static Dto040303 of(Tb411 entity, Tb411_1 tb411_1, String custNm, String empNm, String status, String goodsNm){
        String orderNo = entity.getOrderDt().substring(0,4) + "-" + entity.getOrderDt().substring(4,6) + "-" + entity.getOrderDt().substring(6,8) + "-" + entity.getOrderNo();
        String deadDt = entity.getDeadDt().substring(0,4) + "-" + entity.getDeadDt().substring(4,6) + "-" + entity.getDeadDt().substring(6,8);

        return Dto040303.builder()
                .seq(entity.getSeq())
                .orderNo(orderNo)
                .custNm(custNm)
                .empNm(empNm)
                .expDt(entity.getExpDt())
                .payCondit(entity.getPayCondit())
                .note(entity.getNote())
                .status(status)
                .goodsNm(goodsNm)
                .standard(tb411_1.getStandard())
                .qty(tb411_1.getQty())
                .price(tb411_1.getPrice())
                .amt(tb411_1.getAmt())
                .vat(tb411_1.getVat())
                .deadDt(deadDt)
                .build();
    }
}
