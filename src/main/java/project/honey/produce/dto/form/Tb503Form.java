package project.honey.produce.dto.form;

import lombok.*;
import project.honey.business.entity.Tb405;
import project.honey.produce.dto.Tb503_1Dto;
import project.honey.produce.entity.Tb503;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb503Form {

    private Integer seq;
    private String goodsCd;
    private String goodsNm;
    private String productCd;
    private String productNm;
    private Integer qty;
    private List<Tb503_1Dto> tb503_1Dtos;

    public static Tb503Form of(Tb503 tb503, Tb405 tb405,
                               List<Tb503_1Dto> dtos, Map<String, String> productMap) {
        return Tb503Form.builder()
                .seq(tb503.getSeq())
                .goodsCd(tb405.getGoodsCd())
                .goodsNm(tb405.getGoodsNm())
                .productCd(tb503.getProductCd())
                .productNm(productMap.get(tb503.getProductCd()))
                .qty(tb503.getQty())
                .tb503_1Dtos(dtos)
                .build();
    }
}
