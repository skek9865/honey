package project.honey.business.dto.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb415;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb415Dto {
    private Integer seq;
    private String orderDt;
    private Integer orderNo;
    private String custCd;
    private String custNm;
    private String empNo;
    private String whouseCd;
    private String saleType;
    private String excgCd;
    private String note;
    private String projectCd;
    private String deadDt;
    private String status;
    private Boolean closeYn;
    private String prtEmp;
    private String prtDt;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb415Dto (String date, Integer orderNo){
        this.orderDt = date;
        this.deadDt = date;
        this.orderNo = orderNo;
    }

    public static Tb415Dto of(Tb415 entity, String custNm){
        Boolean closeYn = false;
        if(entity.getCloseYn().equals("Y")) closeYn = true;
        String orderDt = entity.getOrderDt().substring(0,4) + "-" + entity.getOrderDt().substring(4,6) + "-" + entity.getOrderDt().substring(6,8);
        String deadDt = entity.getDeadDt().substring(0,4) + "-" + entity.getDeadDt().substring(4,6) + "-" + entity.getDeadDt().substring(6,8);

        return Tb415Dto.builder()
                .seq(entity.getSeq())
                .orderDt(orderDt)
                .orderNo(entity.getOrderNo())
                .custCd(entity.getCustCd())
                .custNm(custNm)
                .empNo(entity.getEmpNo())
                .whouseCd(entity.getWhouseCd())
                .saleType(entity.getSaleType())
                .excgCd(entity.getExcgCd())
                .note(entity.getNote())
                .projectCd(entity.getProjectCd())
                .deadDt(deadDt)
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
