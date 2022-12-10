package project.honey.produce.dto;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.produce.entity.Tb504_1;
import project.honey.produce.entity.Tb504_2;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb504_2Dto {

    private Integer seq;
    private Integer fkTb504_1;
    private String workDt;
    private Integer workDtNo;
    private Integer qty;
    private Integer rQty;
    private String status;
    private String machineNo;
    private String empNo;
    private String note;

    public static Tb504_2Dto of(Tb504_2 tb504_2, Integer fk) {
        return Tb504_2Dto.builder()
                .seq(tb504_2.getSeq())
                .fkTb504_1(fk)
                .workDt(GlobalMethod.makeYmd(tb504_2.getWorkDt(), "yyyy-MM-dd"))
                .workDtNo(tb504_2.getWorkDtNo())
                .qty(tb504_2.getQty())
                .rQty(tb504_2.getRQty())
                .status(tb504_2.getStatus())
                .machineNo(tb504_2.getMachineNo())
                .empNo(tb504_2.getEmpNo())
                .note(tb504_2.getNote())
                .build();
    }

    public static Tb504_2 toTb504_2(Tb504_2Dto dto, Tb504_1 tb504_1, String workDt, Integer workDtNo) {
        return Tb504_2.builder()
                .tb504_1(tb504_1)
                .workDt(workDt)
                .workDtNo(workDtNo)
                .qty(dto.getQty())
                .rQty(dto.getRQty())
                .status(dto.getStatus())
                .machineNo(dto.getMachineNo())
                .empNo(dto.getEmpNo())
                .note(dto.getNote())
                .build();
    }
}
