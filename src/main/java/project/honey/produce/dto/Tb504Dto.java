package project.honey.produce.dto;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.produce.entity.Tb504;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb504Dto {

    private Integer seq;
    private String workDtNo;
    private String empNm;
    private String goodsNm;
    private String deadDt;
    private Integer qty;
    private Integer rQty;
    private String status;

    public static Tb504Dto of(Tb504 tb504, String goodsNm, Integer qty, Integer rQty,
                              Map<String, String> empMap,Map<String, String> statusMap) {
        String deadDt = GlobalMethod.makeYmd(tb504.getDeadDt(),"yyyy-MM-dd");
        String workDtNo = deadDt + "-" + tb504.getWorkDtNo();
        return Tb504Dto.builder()
                .seq(tb504.getSeq())
                .workDtNo(workDtNo)
                .empNm(empMap.get(tb504.getEmpNo()))
                .goodsNm(goodsNm)
                .deadDt(deadDt)
                .qty(qty)
                .rQty(rQty)
                .status(statusMap.get(tb504.getStatus()))
                .build();
    }
}
