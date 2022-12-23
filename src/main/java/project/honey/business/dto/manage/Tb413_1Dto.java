package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb413_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb413_1Dto {
    private Integer seq;
    private Integer tb413;
    private String goodsCd;
    private String goodsNm;
    private String standard;
    private Integer qty;
    private String note;
    private String productDt;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb413_1Dto(int num){
        this.qty = num;
    }

    public static Tb413_1Dto of(Tb413_1 entity, String goodsNm){
        String productDt = "";
        if(entity.getProductDt().length() == 8) productDt = entity.getProductDt().substring(0,4) + "-" + entity.getProductDt().substring(4,6) + "-" + entity.getProductDt().substring(6,8);

        return Tb413_1Dto.builder()
                .seq(entity.getSeq())
                .tb413(entity.getTb413().getSeq())
                .goodsCd(entity.getGoodsCd())
                .goodsNm(goodsNm)
                .standard(entity.getStandard())
                .qty(entity.getQty())
                .note(entity.getNote())
                .productDt(productDt)
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
