package project.honey.stock.dto;


import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.stock.dto.form.Tb602Form;
import project.honey.stock.entity.Tb602;

import java.util.ArrayList;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb602Dto {

    private Integer seq;
    private String wHouseNo;
    private String custNm;
    private String wHouseOut;
    private String goodsNm;
    private Integer qty;
    private String empNm;
    private String note;
    private String status;

    public static Tb602Dto of(Integer seq, Tb602 tb602, String goodsNm, Integer qty,
                              Map<String, String> wHouseMap, Map<String, String> statusMap, Map<String, String> custMap,
                              Map<String, String> empMap) {
        String wHouseDt = GlobalMethod.makeYmd(tb602.getWHouseDt(),"yyyy-MM-dd");
        String wHouseNo = wHouseDt + "-" + tb602.getWHouseNo();
        return Tb602Dto.builder()
                .seq(seq)
                .wHouseNo(wHouseNo)
                .custNm(custMap.get(tb602.getCustCd()))
                .wHouseOut(wHouseMap.get(tb602.getWHouseOut()))
                .goodsNm(goodsNm)
                .qty(qty)
                .empNm(empMap.get(tb602.getEmpNo()))
                .note(tb602.getNote())
                .status(statusMap.get(tb602.getStatus()))
                .build();
    }

    public static Tb602 toTb602(Tb602Form form) {

        String closeYn = form.getCloseYn() != null ? form.getCloseYn() : "N";

        return Tb602.builder()
                .wHouseDt(GlobalMethod.replaceYmd(form.getWHouseDt(), "-"))
                .wHouseNo(form.getWHouseNo())
                .custCd(form.getCustCd())
                .empNo(form.getEmpNo())
                .wHouseOut(form.getWHouseOut())
                .projectCd(form.getProjectCd())
                .note(form.getNote())
                .status(form.getStatus())
                .closeYn(closeYn)
                .prtEmp(null)
                .prtDt(null)
                .tb602_1s(new ArrayList<>())
                .build();
    }
}
