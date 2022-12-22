package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb412;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb412Dto {

    private Integer seq;
    private String saleDt;
    private Integer saleNo;
    private String custCd;
    private String custNm;
    private String empNo;
    private String whouseCd;
    private String saleType;
    private String excgCd;
    private String outNm;
    private String projectCd;
    private String zipCd;
    private String address;
    private String address1;
    private String name;
    private String takeOk;
    private String note;
    private String note2;
    private String status;
    private Boolean closeYn;
    private String prtEmp;
    private String prtDt;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb412Dto (String saleDt, Integer saleNo){
        this.saleDt = saleDt;
        this.saleNo = saleNo;
    }

    public static Tb412Dto of(Tb412 entity, String custNm){
        Boolean closeYn = false;
        if(entity.getCloseYn().equals("Y")) closeYn = true;
        String saleDt = entity.getSaleDt().substring(0,4) + "-" + entity.getSaleDt().substring(4,6) + "-" +entity.getSaleDt().substring(6,8);

        return Tb412Dto.builder()
                .seq(entity.getSeq())
                .saleDt(saleDt)
                .saleNo(entity.getSaleNo())
                .custCd(entity.getCustCd())
                .custNm(custNm)
                .empNo(entity.getEmpNo())
                .whouseCd(entity.getWhouseCd())
                .saleType(entity.getSaleType())
                .excgCd(entity.getExcgCd())
                .outNm(entity.getOutNm())
                .projectCd(entity.getProjectCd())
                .zipCd(entity.getZipCd())
                .address(entity.getAddress())
                .address1(entity.getAddress1())
                .name(entity.getName())
                .takeOk(entity.getTakeOk())
                .note(entity.getNote())
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
