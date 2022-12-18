package project.honey.stock.dto;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.Query506_1Dto;
import project.honey.stock.dto.query.Query604Dto;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Dto060111 {

    private Integer seq;
    private String wHouseNo;
    private String goodsNm;
    private String wHouseOut;
    private Integer stQty;
    private Integer reQty;
    private Integer adQty;
    private Integer wPrice;
    private String note;

    public static Dto060111 of(Query604Dto dto, Map<String, String> wHouseMap) {

        String wHouseDt = GlobalMethod.makeYmd(dto.getWHouseDt(),"yyyy-MM-dd");
        String wHouseNo = wHouseDt + "-" + dto.getWHouseNo();

        int wPrice = dto.getWPrice() != null ? dto.getWPrice() : 0;

        return Dto060111.builder()
                .seq(dto.getSeq())
                .wHouseNo(wHouseNo)
                .goodsNm(dto.getGoodsNm())
                .wHouseOut(wHouseMap.get(dto.getWHouseOut()))
                .stQty(dto.getStQty())
                .reQty(dto.getReQty())
                .adQty(dto.getAdQty())
                .wPrice(wPrice * dto.getReQty())
                .note(dto.getNote())
                .build();
    }
}
