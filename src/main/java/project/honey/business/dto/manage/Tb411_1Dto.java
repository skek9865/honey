package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb411;
import project.honey.business.entity.manage.Tb411_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb411_1Dto {

    private Integer seq;
    private Integer tb411;
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

    public Tb411_1Dto(int num){
        this.qty = num;
        this.price = num;
        this.amt = num;
        this.vat = num;
    }

    public static Tb411_1Dto of(Tb411_1 entity, String goodsNm){

        String deadDt = "";
        if(!entity.getDeadDt().equals("")) {
            deadDt = entity.getDeadDt().substring(0,4) + "-" + entity.getDeadDt().substring(4,6) + "-" + entity.getDeadDt().substring(6,8);
        }

        return Tb411_1Dto.builder()
                .seq(entity.getSeq())
                .tb411(entity.getTb411().getSeq())
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
