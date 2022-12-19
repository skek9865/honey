package project.honey.produce.dto;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.produce.entity.Tb506;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Dto050303 {

    private Integer seq;
    private String wHouseNo;
    private String wHouseOut;
    private String wHouseIn;
    private String goodsNm;
    private Integer qty;
    private Integer wPrice;
    private String note;

    public static Dto050303 of(Tb506 tb506, String goodsNm, Integer qty, Integer wPrice, Map<String, String> wHouseMap) {
        String wHouseDt = GlobalMethod.makeYmd(tb506.getWHouseDt(),"yyyy-MM-dd");
        String wHouseNo = wHouseDt + "-" + tb506.getWHouseNo();
        return Dto050303.builder()
                .wHouseNo(wHouseNo)
                .wHouseOut(wHouseMap.get(tb506.getWHouseOut()))
                .wHouseIn(wHouseMap.get(tb506.getWHouseIn()))
                .goodsNm(goodsNm)
                .qty(qty)
                .wPrice(wPrice)
                .note(tb506.getNote())
                .build();
    }
}
