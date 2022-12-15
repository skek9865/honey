package project.honey.produce.dto;

import lombok.*;
import project.honey.business.entity.basic.Tb405;
import project.honey.comm.GlobalMethod;
import project.honey.produce.entity.Tb503;
import project.honey.produce.entity.Tb503_1;
import project.honey.produce.entity.Tb506;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Dto050304 {

    private Integer seq;
    private String pGoodsNm;    // 생산품목
    private String cGoodsNm;    // 소모품목
    private Integer pQty ;  // 생산수량
    private Integer sQty;   // 표준소모수량
    private Integer cQty;   // 실제소모수량
    private Integer cPrice; // 소모금액
    private Integer dPrice; // 금액차이

    public static Dto050304 of(Tb503 tb503, Tb405 tb405By503, Tb503_1 tb503_1, Tb405 tb405By503_1) {
        // 생산수량
        int pQty = tb503.getQty();

        // 표준소모수량
        int sQty = 0;

        // 실제 소모수량
        int cQty = tb503_1.getQty();

        // 생산품목단가
        int pPrice = tb405By503.getWPrice();

        // 소모품목단가
        int cPrice = tb405By503_1.getWPrice();

        // 금액차이
        int dPrice = (pQty * pPrice) - (cQty * cPrice);

        return Dto050304.builder()
                .seq(tb503.getSeq())
                .pGoodsNm(tb405By503.getGoodsNm())
                .cGoodsNm(tb405By503_1.getGoodsNm())
                .pQty(pQty)
                .sQty(sQty)
                .cQty(cQty)
                .cPrice(cPrice)
                .dPrice(dPrice)
                .build();
    }
}
