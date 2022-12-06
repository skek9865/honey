package project.honey.business.dto;

import lombok.*;
import project.honey.business.entity.Tb405;

import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb405Dto {

    private Integer seq;
    private String goodsCd;
    private String goodsNm;
    private String standard;
    private String unit;
    private String classSeq;
    private String setYn;
    private String stockYn;
    private String product;
    private String itemGb1;
    private String itemGb2;
    private Integer stockQty;
    private Integer aQty;
    private Integer wPrice;
    private String wPriceVat;
    private Integer fPrice;
    private String fPriceVat;
    private String imgNmSave;
    private String imgNmOut;

    public static Tb405Dto of(Tb405 entity, Map<String, String> classMap,
                              Map<String, String> productMap, Map<String, String> itemGbMap) {
        return Tb405Dto.builder()
                .seq(entity.getSeq())
                .goodsCd(entity.getGoodsCd())
                .goodsNm(entity.getGoodsNm())
                .standard(entity.getStandard())
                .unit(entity.getUnit())
                .classSeq(classMap.get(entity.getClassSeq()))
                .setYn(entity.getSetYn())
                .stockYn(entity.getStockYn())
                .product(productMap.get(entity.getProduct()))
                .itemGb1(itemGbMap.get(entity.getItemGb1()))
                .itemGb2(itemGbMap.get(entity.getItemGb2()))
                .stockQty(entity.getStockQty())
                .aQty(entity.getAQty())
                .wPrice(entity.getWPrice())
                .wPriceVat(entity.getWPriceVat())
                .fPrice(entity.getFPrice())
                .fPriceVat(entity.getFPriceVat())
                .imgNmSave(entity.getImgNmSave())
                .imgNmOut(entity.getImgNmOut())
                .build();
    }
}
