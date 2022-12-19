package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb410_1;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb410_1Dto {

    private Integer seqC;
    private Integer tb410;
    private String goodsCd;
    private String goodsNm;
    private String standard;
    private Integer qty;
    private Integer price;
    private Integer amt;
    private Integer vat;
    private String noteC;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb410_1Dto(int num){
        this.qty = num;
        this.price = num;
        this.amt = num;
        this.vat = num;
    }

    public static Tb410_1Dto of (Tb410_1 entity, String goodsNm){
        return Tb410_1Dto.builder()
                .seqC(entity.getSeq())
                .tb410(entity.getTb410().getSeq())
                .goodsCd(entity.getGoodsCd())
                .goodsNm(goodsNm)
                .standard(entity.getStandard())
                .qty(entity.getQty())
                .price(entity.getPrice())
                .amt(entity.getAmt())
                .vat(entity.getVat())
                .noteC(entity.getNote())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
