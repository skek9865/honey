package project.honey.stock.dto;

import lombok.*;
import project.honey.stock.entity.Tb603;
import project.honey.stock.entity.Tb603_1;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb603_1Dto {

    private Integer seq;
    private Integer fkTb601;
    private String goodsCd;
    private String goodsNm;
    private Integer qty;
    private String goodsCds;
    private String goodsNms;
    private Integer rQty;
    private String faulyType;
    private String color;
    private String note;

    public static Tb603_1Dto of(Tb603_1 tb603_1, Tb603 tb603, Map<String, String> goodsMap) {
        return Tb603_1Dto.builder()
                .seq(tb603_1.getSeq())
                .fkTb601(tb603.getSeq())
                .goodsCd(tb603_1.getGoodsCd())
                .goodsNm(goodsMap.get(tb603_1.getGoodsCd()))
                .qty(tb603_1.getQty())
                .goodsCds(tb603_1.getGoodsCds())
                .goodsNms(goodsMap.get(tb603_1.getGoodsCds()))
                .rQty(tb603_1.getRQty())
                .faulyType(tb603_1.getFaulyType())
                .color(tb603_1.getColor())
                .note(tb603.getNote())
                .build();
    }

    public static Tb603_1 toTb603_1(Tb603_1Dto dto, Tb603 tb603) {
        return Tb603_1.builder()
                .tb603(tb603)
                .goodsCd(dto.getGoodsCd())
                .qty(dto.getQty())
                .goodsCds(dto.getGoodsCds())
                .rQty(dto.getRQty())
                .faulyType(dto.getFaulyType())
                .color(dto.getColor())
                .note(dto.getNote())
                .build();
    }
}
