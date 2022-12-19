package project.honey.stock.dto;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.stock.entity.Tb603;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Dto060108 {

    private Integer seq;
    private String wHouseNo;
    private String goodsNm;
    private Integer qty;
    private String note;

    public static Dto060108 of(Tb603 tb603, String goodsNm, Integer qty) {
        String wHouseDt = GlobalMethod.makeYmd(tb603.getWHouseDt(),"yyyy-MM-dd");
        String wHouseNo = wHouseDt + "-" + tb603.getWHouseNo();
        return Dto060108.builder()
                .wHouseNo(wHouseNo)
                .goodsNm(goodsNm)
                .qty(qty)
                .note(tb603.getNote())
                .build();
    }
}
