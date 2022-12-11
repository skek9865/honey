package project.honey.produce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import project.honey.business.entity.Tb405;
import project.honey.produce.entity.Tb503;
import project.honey.produce.entity.Tb503_1;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb503_1Dto {

    private Integer seq;
    private Integer fkTb503;
    private String goodsCd;
    private String goodsNm;
    private String standard;
    private String unit;
    private Integer qty;
    private String productCd;
    private String productNm;
    private String location;
    private String note;

    @QueryProjection
    public Tb503_1Dto(Integer seq, Integer fkTb503, String goodsCd,
                      String goodsNm, String standard, String unit, Integer qty, String productNm, String location, String note) {
        this.seq = seq;
        this.fkTb503 = fkTb503;
        this.goodsCd = goodsCd;
        this.goodsNm = goodsNm;
        this.standard = standard;
        this.unit = unit;
        this.qty = qty;
        this.productNm = productNm;
        this.location = location;
        this.note = note;
    }

    public static Tb503_1Dto of(Tb503_1 tb503_1, Tb405 tb405, Map<String, String> productMap) {
        return Tb503_1Dto.builder()
                .seq(tb503_1.getSeq())
                .fkTb503(tb503_1.getTb503().getSeq())
                .goodsCd(tb503_1.getGoodsCd())
                .standard(tb405.getStandard())
                .unit(tb405.getUnit())
                .qty(tb503_1.getQty())
                .productNm(productMap.get(tb503_1.getProductCd()))
                .location(tb503_1.getLocation())
                .note(tb503_1.getNote())
                .build();
    }

    public static Tb503_1 toTb503_1(Tb503_1Dto dto, Tb503 tb503) {
        return Tb503_1.builder()
                .tb503(tb503)
                .goodsCd(dto.getGoodsCd())
                .productCd(dto.getProductCd())
                .qty(dto.getQty())
                .location(dto.getLocation())
                .note(dto.getNote())
                .build();
    }
}
