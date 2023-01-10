package project.honey.produce.dto.form;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.Tb504_1Dto;
import project.honey.produce.entity.Tb504;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb504Form {

    private Integer seq;
    private String workDt;
    private Integer workDtNo;
    private String custCd;
    private String custNm;
    private String productCd;
    private String empNo;
    private String deadDt;
    private String status;
    private String closeYn;
    private String note;
    List<Tb504_1Dto> tb504_1Dtos;

    public static Tb504Form of(Tb504 tb504, List<Tb504_1Dto> dtoList, Map<String, String> custMap) {
        return Tb504Form.builder()
                .seq(tb504.getSeq())
                .workDt(GlobalMethod.makeYmd(tb504.getWorkDt(), "yyyy-MM-dd"))
                .workDtNo(tb504.getWorkDtNo())
                .custCd(tb504.getCustCd())
                .custNm(custMap.get(tb504.getCustCd()))
                .productCd(tb504.getProductCd())
                .empNo(tb504.getEmpNo())
                .deadDt(GlobalMethod.makeYmd(tb504.getDeadDt(), "yyyy-MM-dd"))
                .status(tb504.getStatus())
                .closeYn(tb504.getCloseYn())
                .note(tb504.getNote())
                .tb504_1Dtos(dtoList)
                .build();
    }
}
