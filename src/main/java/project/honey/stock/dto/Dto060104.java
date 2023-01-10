package project.honey.stock.dto;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.produce.entity.Tb506;
import project.honey.stock.entity.Tb601;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Dto060104 {

    private Integer seq;
    private String wHouseNo;
    private String wHouseOut;
    private String wHouseIn;
    private String goodsNm;
    private Integer qty;
    private Integer wPrice;
    private String note;

    public static Dto060104 of(Tb601 tb601, String goodsNm, Integer qty, Integer wPrice, Map<String, String> wHouseMap) {
        String wHouseDt = GlobalMethod.makeYmd(tb601.getWHouseDt(),"yyyy-MM-dd");
        String wHouseNo = wHouseDt + "-" + tb601.getWHouseNo();
        return Dto060104.builder()
                .wHouseNo(wHouseNo)
                .wHouseOut(wHouseMap.get(tb601.getWHouseOut()))
                .wHouseIn(wHouseMap.get(tb601.getWHouseIn()))
                .goodsNm(goodsNm)
                .qty(qty)
                .wPrice(wPrice)
                .note(tb601.getNote())
                .build();
    }
}
