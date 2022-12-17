package project.honey.stock.dto;


import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.stock.dto.form.Tb603Form;
import project.honey.stock.entity.Tb603;

import java.util.ArrayList;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb603Dto {

    private Integer seq;
    private String wHouseNo;
    private String wHouseOut;
    private String goodsNm;
    private Integer qty;
    private String prcsmTd;
    private String status;

    public static Tb603Dto of(Integer seq, Tb603 tb603, String goodsNm, Integer qty,
                              Map<String, String> wHouseMap, Map<String, String> statusMap, Map<String, String> prcsmTdMap) {
        String wHouseDt = GlobalMethod.makeYmd(tb603.getWHouseDt(),"yyyy-MM-dd");
        String wHouseNo = wHouseDt + "-" + tb603.getWHouseNo();
        return Tb603Dto.builder()
                .seq(seq)
                .wHouseNo(wHouseNo)
                .wHouseOut(wHouseMap.get(tb603.getWHouseOut()))
                .goodsNm(goodsNm)
                .qty(qty)
                .prcsmTd(prcsmTdMap.get(tb603.getPrcsmTd()))
                .status(statusMap.get(tb603.getStatus()))
                .build();
    }

    public static Tb603 toTb603(Tb603Form form) {

        String closeYn = form.getCloseYn() != null ? form.getCloseYn() : "N";

        return Tb603.builder()
                .wHouseDt(GlobalMethod.replaceYmd(form.getWHouseDt(), "-"))
                .wHouseNo(form.getWHouseNo())
                .empNo(form.getEmpNo())
                .wHouseOut(form.getWHouseOut())
                .prcsmTd(form.getPrcsmTd())
                .projectCd(form.getProjectCd())
                .note(form.getNote())
                .status(form.getStatus())
                .closeYn(closeYn)
                .prtEmp(null)
                .prtDt(null)
                .tb603_1s(new ArrayList<>())
                .build();
    }
}
