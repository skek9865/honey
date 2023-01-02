package project.honey.business.dto.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb416;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb416Dto {

    private Integer seq;
    private String buyDt;
    private Integer buyNo;
    private String custCd;
    private String custNm;
    private String empNo;
    private String whouseCd;
    private String saleType;
    private String excgCd;
    private String note;
    private String projectCd;
    private String status;
    private Boolean closeYn;
    private String prtEmp;
    private String prtDt;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb416Dto (String buyDt, Integer buyNo){
        this.buyDt = buyDt;
        this.buyNo = buyNo;
    }

    public static Tb416Dto of(Tb416 entity, String custNm){
        Boolean closeYn = false;
        if(entity.getCloseYn().equals("Y")) closeYn = true;
        String buyDt = entity.getBuyDt().substring(0,4) + "-" + entity.getBuyDt().substring(4,6) + "-" + entity.getBuyDt().substring(6,8);

        return Tb416Dto.builder()
                .seq(entity.getSeq())
                .buyDt(buyDt)
                .buyNo(entity.getBuyNo())
                .custCd(entity.getCustCd())
                .custNm(custNm)
                .empNo(entity.getEmpNo())
                .whouseCd(entity.getWhouseCd())
                .saleType(entity.getSaleType())
                .excgCd(entity.getExcgCd())
                .note(entity.getNote())
                .projectCd(entity.getProjectCd())
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
