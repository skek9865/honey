package project.honey.stock.dto;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.stock.entity.Tb601;
import project.honey.stock.entity.Tb603;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Dto060106 {

    private Integer seq;
    private String wHouseNo;
    private String prcsmTd;
    private String goodsNm;
    private Integer qty;
    private String note;

    public static Dto060106 of(Tb603 tb603, String goodsNm, Integer qty, Map<String, String> prcsmTdMap) {
        String wHouseDt = GlobalMethod.makeYmd(tb603.getWHouseDt(),"yyyy-MM-dd");
        String wHouseNo = wHouseDt + "-" + tb603.getWHouseNo();
        return Dto060106.builder()
                .wHouseNo(wHouseNo)
                .prcsmTd(prcsmTdMap.get(tb603.getPrcsmTd()))
                .goodsNm(goodsNm)
                .qty(qty)
                .note(tb603.getNote())
                .build();
    }
}
