package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb411;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb411Dto {

    private Integer seq;
    private String orderDt;
    private Integer orderNo;
    private String custCd;
    private String empNo;
    private String whouseCd;
    private String saleType;
    private String excgCd;
    private String projectCd;
    private String note;
    private String payCondit;
    private String expDt;
    private String deadDt;
    private String note2;
    private String status;
    private String closeYn;
    private String prtEmp;
    private String prtDt;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public static Tb411Dto of(Tb411 entity){
        return Tb411Dto.builder()
                .orderDt(entity.getOrderDt())
                .orderNo(entity.getOrderNo())
                .custCd(entity.getCustCd())
                .empNo(entity.getEmpNo())
                .whouseCd(entity.getWhouseCd())
                .saleType(entity.getSaleType())
                .excgCd(entity.getExcgCd())
                .projectCd(entity.getProjectCd())
                .note(entity.getNote())
                .payCondit(entity.getPayCondit())
                .expDt(entity.getExpDt())
                .deadDt(entity.getDeadDt())
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
