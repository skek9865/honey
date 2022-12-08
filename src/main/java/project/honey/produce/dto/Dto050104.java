package project.honey.produce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import project.honey.business.entity.Tb405;
import project.honey.produce.entity.Tb503;
import project.honey.produce.entity.Tb503_1;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Dto050104 {

    // Tb503
    private Integer seq;
    private String classSeq;
    private String goodsCd;
    private String goodsNm;
    private String standard;
    private Integer qty;

    // Tb503_1
    private String fClassSeq;
    private String fGoodsCd;
    private String fGoodsNm;
    private String fStandard;
    private Integer fQty;

    public static Dto050104 of(Tb503 tb503, Tb405 tb405By503, Tb503_1 tb503_1, Tb405 tb405By503_1, Map<String, String> classMap) {
        return Dto050104.builder()
                .seq(tb503.getSeq())
                .classSeq(classMap.get(tb405By503.getClassSeq()))
                .goodsCd(tb503.getGoodsCd())
                .goodsNm(tb405By503.getGoodsNm())
                .standard(tb405By503.getStandard())
                .qty(tb503.getQty())
                .fClassSeq(classMap.get(tb405By503_1.getClassSeq()))
                .fGoodsCd(tb503_1.getGoodsCd())
                .fGoodsNm(tb405By503_1.getGoodsNm())
                .fStandard(tb405By503_1.getStandard())
                .fQty(tb503_1.getQty())
                .build();
    }
}
