package project.honey.stock.dto;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.stock.entity.Tb601;
import project.honey.stock.entity.Tb602;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Dto060105 {

    private Integer seq;
    private String wHouseNo;
    private String custNm;
    private String goodsNm;
    private String wHouseOut;
    private Integer qty;
    private Integer wPrice;
    private String note;

    public static Dto060105 of(Tb602 tb602, String goodsNm, Integer qty, Integer wPrice, Map<String, String> wHouseMap, Map<String, String> custMap) {
        String wHouseDt = GlobalMethod.makeYmd(tb602.getWHouseDt(),"yyyy-MM-dd");
        String wHouseNo = wHouseDt + "-" + tb602.getWHouseNo();
        return Dto060105.builder()
                .wHouseNo(wHouseNo)
                .custNm(custMap.get(tb602.getCustCd()))
                .goodsNm(goodsNm)
                .wHouseOut(wHouseMap.get(tb602.getWHouseOut()))
                .qty(qty)
                .wPrice(wPrice)
                .note(tb602.getNote())
                .build();
    }
}
