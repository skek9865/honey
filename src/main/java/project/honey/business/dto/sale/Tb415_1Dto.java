package project.honey.business.dto.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb415_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb415_1Dto {

    private Integer seq;
    private Integer tb415;
    private String goodsCd;
    private String goodsNm;
    private String standard;
    private Integer qty;
    private Integer price;
    private Integer amt;
    private Integer vat;
    private String deadDt;
    private String note;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb415_1Dto (int num){
        this.qty = num;
        this.price = num;
        this.amt = num;
        this.vat = num;
    }

    public static Tb415_1Dto of(Tb415_1 entity, String goodsNm){
        String deadDt = entity.getDeadDt().substring(0,4) + "-" + entity.getDeadDt().substring(4,6) + "-" + entity.getDeadDt().substring(6,8);

        return Tb415_1Dto.builder()
                .seq(entity.getSeq())
                .tb415(entity.getTb415().getSeq())
                .goodsCd(entity.getGoodsCd())
                .goodsNm(goodsNm)
                .standard(entity.getStandard())
                .qty(entity.getQty())
                .price(entity.getPrice())
                .amt(entity.getAmt())
                .vat(entity.getVat())
                .deadDt(deadDt)
                .note(entity.getNote())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
