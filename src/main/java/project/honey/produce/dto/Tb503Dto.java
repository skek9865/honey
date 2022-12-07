package project.honey.produce.dto;

import lombok.*;
import project.honey.business.entity.Tb405;
import project.honey.produce.dto.input.Tb503Input;
import project.honey.produce.entity.Tb503;
import project.honey.produce.entity.Tb503_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb503Dto {

    private Integer seq;
    private String goodsCd;
    private String goodsNm;
    private String classSeq;
    private String productCd;
    private String productNm;
    private Integer qty;

    public static Tb503Dto of(Tb405 tb405, Integer seq, Map<String, String> classMap,
                              Map<String, String> productMap, Integer qty) {
        return Tb503Dto.builder()
                .seq(seq)
                .goodsCd(tb405.getGoodsCd())
                .goodsNm(tb405.getGoodsNm())
                .classSeq(classMap.get(tb405.getClassSeq()))
                .productCd(tb405.getProduct())
                .productNm(productMap.get(tb405.getProduct()))
                .qty(qty)
                .build();
    }

    public static Tb503 toTb503(Tb503Input dto) {
        return Tb503.builder()
                .goodsCd(dto.getGoodsCd())
                .productCd(dto.getProductCd())
                .qty(dto.getQty())
                .tb503_1s(new ArrayList<>())
                .build();
    }
}
