package project.honey.produce.dto;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.form.Tb504Form;
import project.honey.produce.dto.form.Tb505Form;
import project.honey.produce.entity.Tb504;
import project.honey.produce.entity.Tb505;

import java.util.ArrayList;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb505Dto {

    private Integer seq;
    private String wHouseNo;
    private String wHouseOut;
    private String wHouseIn;
    private String goodsNm;
    private Integer qty;
    private String status;

    public static Tb505Dto of(Integer seq, Tb505 tb505, String goodsNm, Integer qty,
                              Map<String, String> wHouseMap, Map<String, String> statusMap) {
        String wHouseDt = GlobalMethod.makeYmd(tb505.getWHouseDt(),"yyyy-MM-dd");
        String wHouseNo = wHouseDt + "-" + tb505.getWHouseNo();
        return Tb505Dto.builder()
                .seq(seq)
                .wHouseNo(wHouseNo)
                .wHouseOut(wHouseMap.get(tb505.getWHouseOut()))
                .wHouseIn(wHouseMap.get(tb505.getWHouseIn()))
                .goodsNm(goodsNm)
                .qty(qty)
                .status(statusMap.get(tb505.getStatus()))
                .build();
    }

    public static Tb505 toTb505(Tb505Form form) {

        String closeYn = form.getCloseYn() != null ? form.getCloseYn() : "N";

        return Tb505.builder()
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
                .tb505_1s(new ArrayList<>())
                .build();
    }
}
