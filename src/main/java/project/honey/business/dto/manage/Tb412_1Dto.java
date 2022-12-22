package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.entity.manage.Tb412_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb412_1Dto {

    private Integer seq;
    private Integer tb412;
    private String goodsCd;
    private String goodsNm;
    private String standard;
    private Integer qty;
    private Integer price;
    private Integer amt;
    private Integer vat;
    private Integer amtSum;
    private String note;
    private Integer priceVat;
    private String shipYn;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb412_1Dto(int num){
        this.qty = num;
        this.price = num;
        this.amt = num;
        this.vat = num;
        this.amtSum = num;
        this.priceVat = num;
    }

    public static Tb412_1Dto of (Tb412_1 entity, String goodsNm){
        return Tb412_1Dto.builder()
                .seq(entity.getSeq())
                .tb412(entity.getTb412().getSeq())
                .goodsCd(entity.getGoodsCd())
                .goodsNm(goodsNm)
                .standard(entity.getStandard())
                .qty(entity.getQty())
                .price(entity.getPrice())
                .amt(entity.getAmt())
                .vat(entity.getVat())
                .amtSum(entity.getAmtSum())
                .note(entity.getNote())
                .priceVat(entity.getPriceVat())
                .shipYn(entity.getShipYn())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
