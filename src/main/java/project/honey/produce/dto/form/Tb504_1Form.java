package project.honey.produce.dto.form;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.Tb504_2Dto;
import project.honey.produce.entity.Tb504;
import project.honey.produce.entity.Tb504_1;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb504_1Form {

    private Integer seq;
    private String workDt1;
    private Integer workDtNo;
    private String custNm;
    private String productCd;
    private String empNo;
    private String deadDt;
    private String goodsNm;
    private Integer qty;
    private String machineNo;
    private String workDt2;
    private List<Tb504_2Dto> tb504_2Dtos;

    public static Tb504_1Form of(Tb504_1 tb504_1, Map<String, String> custMap, Map<String, String> goodsMap,
                                 List<Tb504_2Dto> dtoList) {
        Tb504 tb504 = tb504_1.getTb504();
        return Tb504_1Form.builder()
                .seq(tb504_1.getSeq())
                .workDt1(GlobalMethod.makeYmd(tb504.getWorkDt(), "yyyy-MM-dd"))
                .workDtNo(tb504.getWorkDtNo())
                .custNm(custMap.get(tb504.getCustCd()))
                .productCd(tb504.getProductCd())
                .empNo(tb504_1.getEmpNo())
                .deadDt(GlobalMethod.makeYmd(tb504.getDeadDt(), "yyyy-MM-dd"))
                .goodsNm(goodsMap.get(tb504_1.getGoodsCd()))
                .qty(tb504_1.getQty())
                .machineNo(tb504_1.getMachineNo())
                .workDt2(dtoList.get(0) != null ? dtoList.get(0).getWorkDt() : "")
                .tb504_2Dtos(dtoList)
                .build();
    }
}
