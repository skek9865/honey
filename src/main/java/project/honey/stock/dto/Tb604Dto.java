package project.honey.stock.dto;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.stock.entity.Tb604;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb604Dto {

    private Integer seq;
    private String wHouseNo;
    private String empNm;
    private String wHouseOut;
    private String goodsNm;
    private Integer stQty;
    private Integer reQty;
    private Integer adQty;
    private String note;

    public static Tb604Dto of(Tb604 tb604, Map<String, String> empMap, Map<String, String> wHouseMap,
                              Map<String, String> goodsMap) {
        String wHouseDt = GlobalMethod.makeYmd(tb604.getWHouseDt(),"yyyy-MM-dd");
        String wHouseNo = wHouseDt + "-" + tb604.getWHouseNo();

        return Tb604Dto.builder()
                .seq(tb604.getSeq())
                .wHouseNo(wHouseNo)
                .empNm(empMap.get(tb604.getEmpNo()))
                .wHouseOut(wHouseMap.get(tb604.getWHouseOut()))
                .goodsNm(goodsMap.get(tb604.getGoodsCd()))
                .stQty(tb604.getStQty())
                .reQty(tb604.getReQty())
                .adQty(tb604.getAdQty())
                .note(tb604.getNote())
                .build();
    }
}
