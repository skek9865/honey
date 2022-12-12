package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb410;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb410Dto {

    private Integer seqP;
    private String estimDt;
    private Integer estimNo;
    private String custCd;
    private String empNo;
    private String whouseCd;
    private String saleType;
    private String excgCd;
    private String noteP;
    private String payCondit;
    private String name;
    private String expDt;
    private String phone;
    private String email;
    private String note2;
    private String status;
    private String closeYn;
    private String prtEmp;
    private String prtDt;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public static Tb410Dto of (Tb410 entity){
        return Tb410Dto.builder()
                .seqP(entity.getSeq())
                .estimDt(entity.getEstimDt())
                .estimNo(entity.getEstimNo())
                .custCd(entity.getCustCd())
                .empNo(entity.getEmpNo())
                .whouseCd(entity.getWhouseCd())
                .saleType(entity.getSaleType())
                .excgCd(entity.getExcgCd())
                .noteP(entity.getNote())
                .payCondit(entity.getPayCondit())
                .name(entity.getName())
                .expDt(entity.getExpDt())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .note2(entity.getNote2())
                .status(entity.getStatus())
                .closeYn(entity.getCloseYn())
                .prtEmp(entity.getPrtEmp())
                .prtDt(entity.getPrtDt())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
