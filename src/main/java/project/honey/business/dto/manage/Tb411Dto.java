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
    private String custNm;
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
    private Boolean closeYn;
    private String prtEmp;
    private String prtDt;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb411Dto (String orderDt, Integer orderNo){
        this.orderDt = orderDt;
        this.orderNo = orderNo;
        this.deadDt = orderDt;
    }

    public static Tb411Dto of(Tb411 entity, String custNm){
        Boolean closeYn = false;
        if(entity.getCloseYn().equals("Y")) closeYn = true;
        String orderDt = entity.getOrderDt().substring(0,4) + "-" + entity.getOrderDt().substring(4,6) + "-" +entity.getOrderDt().substring(6,8);
        String deadDt = entity.getDeadDt().substring(0,4) + "-" + entity.getDeadDt().substring(4,6) + "-" +entity.getDeadDt().substring(6,8);

        return Tb411Dto.builder()
                .seq(entity.getSeq())
                .orderDt(orderDt)
                .orderNo(entity.getOrderNo())
                .custCd(entity.getCustCd())
                .custNm(custNm)
                .empNo(entity.getEmpNo())
                .whouseCd(entity.getWhouseCd())
                .saleType(entity.getSaleType())
                .excgCd(entity.getExcgCd())
                .projectCd(entity.getProjectCd())
                .note(entity.getNote())
                .payCondit(entity.getPayCondit())
                .expDt(entity.getExpDt())
                .deadDt(deadDt)
                .note2(entity.getNote2())
                .status(entity.getStatus())
                .closeYn(closeYn)
                .prtEmp(entity.getPrtEmp())
                .prtDt(entity.getPrtDt())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
