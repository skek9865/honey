package project.honey.stock.dto;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.stock.entity.Tb603;
import project.honey.stock.entity.Tb603_1;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Dto060107 {

    private Integer seq;
    private String wHouseNo;
    private String prcsmTd;
    private String goodsNm;
    private Integer qty;
    private String goodsNms;
    private Integer rQty;
    private String note;

    public static Dto060107 of(Tb603_1 tb603_1, Map<String, String> prcsmTdMap, Map<String, String> goodsMap) {
        Tb603 tb603 = tb603_1.getTb603();
        String wHouseDt = GlobalMethod.makeYmd(tb603.getWHouseDt(),"yyyy-MM-dd");
        String wHouseNo = wHouseDt + "-" + tb603.getWHouseNo();
        return Dto060107.builder()
                .seq(tb603.getSeq())
                .wHouseNo(wHouseNo)
                .prcsmTd(prcsmTdMap.get(tb603.getPrcsmTd()))
                .goodsNm(goodsMap.get(tb603_1.getGoodsCd()))
                .qty(tb603_1.getQty())
                .goodsNms(goodsMap.get(tb603_1.getGoodsCds()))
                .rQty(tb603_1.getRQty())
                .note(tb603_1.getNote())
                .build();
    }
}
