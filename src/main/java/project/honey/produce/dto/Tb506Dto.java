package project.honey.produce.dto;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.form.Tb505Form;
import project.honey.produce.dto.form.Tb506Form;
import project.honey.produce.entity.Tb505;
import project.honey.produce.entity.Tb506;

import java.util.ArrayList;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb506Dto {

    private Integer seq;
    private String wHouseNo;
    private String wHouseOut;
    private String wHouseIn;
    private String goodsNm;
    private Integer qty;
    private String status;

    public static Tb506Dto of(Integer seq, Tb506 tb506, String goodsNm, Integer qty,
                              Map<String, String> wHouseMap, Map<String, String> statusMap) {
        String wHouseDt = GlobalMethod.makeYmd(tb506.getWHouseDt(),"yyyy-MM-dd");
        String wHouseNo = wHouseDt + "-" + tb506.getWHouseNo();
        return Tb506Dto.builder()
                .seq(seq)
                .wHouseNo(wHouseNo)
                .wHouseOut(wHouseMap.get(tb506.getWHouseOut()))
                .wHouseIn(wHouseMap.get(tb506.getWHouseIn()))
                .goodsNm(goodsNm)
                .qty(qty)
                .status(statusMap.get(tb506.getStatus()))
                .build();
    }

    public static Tb506 toTb506(Tb506Form form) {

        String closeYn = form.getCloseYn() != null ? form.getCloseYn() : "N";

        return Tb506.builder()
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
                .tb506_1s(new ArrayList<>())
                .build();
    }
}
