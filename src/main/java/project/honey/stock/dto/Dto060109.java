package project.honey.stock.dto;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.Query506_1Dto;
import project.honey.produce.entity.Tb503;
import project.honey.produce.entity.Tb506;
import project.honey.stock.entity.Tb603;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Dto060109 {

    private Integer seq;
    private String wHouseNm;
    private String goodsNm;
    private Integer qty;
    private Integer rQty;   //불량수량
    private Double rate;

    public static Dto060109 of(Query506_1Dto dto, Integer rQty, Map<String, String> wHouseMap,
                               Map<String, String> goodsMap) {

        // 널 처리
        int qty = dto.getQty() != null ? dto.getQty() : 0;
        rQty = rQty != null ? rQty : 0;

        Double rate = (double) rQty / qty * 100.0;

        return Dto060109.builder()
                .wHouseNm(wHouseMap.get(dto.getWHouseIn()))
                .goodsNm(goodsMap.get(dto.getGoodsCd()))
                .qty(qty)
                .rQty(rQty)
                .rate(rate)
                .build();
    }
}
