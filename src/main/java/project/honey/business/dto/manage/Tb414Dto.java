package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb414;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb414Dto {

    private Integer seq;
    private String shipDt;
    private Integer shipNo;
    private String custCd;
    private String custNm;
    private String empNo;
    private String whouseCd;
    private String projectCd;
    private String phone;
    private String zipCd;
    private String address;
    private String address1;
    private String note;
    private String status;
    private Boolean closeYn;
    private String prtEmp;
    private String prtDt;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb414Dto (String shipDt, Integer shipNo){
        this.shipDt = shipDt;
        this.shipNo = shipNo;
    }

    public static Tb414Dto of(Tb414 entity, String custNm){
        Boolean closeYn = false;
        String shipDt = "";
        if(entity.getCloseYn().equals("Y")) closeYn = true;
        if(entity.getShipDt().length() == 8) shipDt = entity.getShipDt().substring(0,4) + "-" + entity.getShipDt().substring(4,6) + "-" + entity.getShipDt().substring(6,8);

        return Tb414Dto.builder()
                .seq(entity.getSeq())
                .shipDt(shipDt)
                .shipNo(entity.getShipNo())
                .custCd(entity.getCustCd())
                .custNm(custNm)
                .empNo(entity.getEmpNo())
                .whouseCd(entity.getWhouseCd())
                .projectCd(entity.getProjectCd())
                .phone(entity.getPhone())
                .zipCd(entity.getZipCd())
                .address(entity.getAddress())
                .address1(entity.getAddress1())
                .note(entity.getNote())
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
