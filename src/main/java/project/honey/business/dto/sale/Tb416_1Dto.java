package project.honey.business.dto.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb416;
import project.honey.business.entity.sale.Tb416_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb416_1Dto {

    private Integer seq;
    private Tb416 tb416;
    private String goodsCd;
    private String goodsNm;
    private String standard;
    private Integer qty;
    private Integer price;
    private Integer amt;
    private Integer vat;
    private String note;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb416_1Dto (int num){
        this.qty = num;
        this.price = num;
        this.amt = num;
        this.vat = num;
    }

    public static Tb416_1Dto of(Tb416_1 entity, String goodsNm){
        return Tb416_1Dto.builder()
                .seq(entity.getSeq())
                .goodsCd(entity.getGoodsCd())
                .goodsNm(goodsNm)
                .standard(entity.getStandard())
                .qty(entity.getQty())
                .price(entity.getPrice())
                .amt(entity.getAmt())
                .vat(entity.getVat())
                .note(entity.getNote())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
