package project.honey.stock.dto;


import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.Tb505Dto;
import project.honey.stock.dto.form.Tb601Form;
import project.honey.stock.entity.Tb601;

import java.util.ArrayList;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb601Dto {

    private Integer seq;
    private String wHouseNo;
    private String wHouseOut;
    private String wHouseIn;
    private String goodsNm;
    private Integer qty;
    private String status;

    public static Tb601Dto of(Integer seq, Tb601 tb601, String goodsNm, Integer qty,
                              Map<String, String> wHouseMap, Map<String, String> statusMap) {
        String wHouseDt = GlobalMethod.makeYmd(tb601.getWHouseDt(),"yyyy-MM-dd");
        String wHouseNo = wHouseDt + "-" + tb601.getWHouseNo();
        return Tb601Dto.builder()
                .seq(seq)
                .wHouseNo(wHouseNo)
                .wHouseOut(wHouseMap.get(tb601.getWHouseOut()))
                .wHouseIn(wHouseMap.get(tb601.getWHouseIn()))
                .goodsNm(goodsNm)
                .qty(qty)
                .status(statusMap.get(tb601.getStatus()))
                .build();
    }

    public static Tb601 toTb601(Tb601Form form) {

        String closeYn = form.getCloseYn() != null ? form.getCloseYn() : "N";

        return Tb601.builder()
                .wHouseDt(GlobalMethod.replaceYmd(form.getWHouseDt(), "-"))
                .wHouseNo(form.getWHouseNo())
                .empNo(form.getEmpNo())
                .wHouseOut(form.getWHouseOut())
                .wHouseIn(form.getWHouseIn())
                .projectCd(form.getProjectCd())
                .note(form.getNote())
                .status(form.getStatus())
                .closeYn(closeYn)
                .prtEmp(null)
                .prtDt(null)
                .tb601_1s(new ArrayList<>())
                .build();
    }
}
