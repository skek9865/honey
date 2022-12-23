package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb413;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb413Dto {

    private Integer seq;
    private String shipDt;
    private Integer shipNo;
    private String custCd;
    private String custNm;
    private String empNo;
    private String whouseCd;
    private String projectCd;
    private String phone;
    private String outDt;
    private String zipCd1;
    private String address1;
    private String address11;
    private String shipNm;
    private String sender;
    private String zipCd;
    private String address2;
    private String address21;
    private String name;
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

    public Tb413Dto(String shipDt, Integer shipNo){
        this.shipDt = shipDt;
        this.outDt = shipDt;
        this.shipNo = shipNo;
    }

    public static Tb413Dto of(Tb413 entity, String custNm){
        Boolean closeYn = false;
        String shipDt = "", outDt = "";
        if(entity.getCloseYn().equals("Y")) closeYn = true;
        if(entity.getShipDt().length() == 8) shipDt = entity.getShipDt().substring(0,4) + "-" + entity.getShipDt().substring(4,6) + "-" + entity.getShipDt().substring(6,8);
        if(entity.getOutDt().length() == 8) outDt = entity.getOutDt().substring(0,4) + "-" + entity.getOutDt().substring(4,6) + "-" + entity.getOutDt().substring(6,8);

        return Tb413Dto.builder()
                .seq(entity.getSeq())
                .shipDt(shipDt)
                .shipNo(entity.getShipNo())
                .custCd(entity.getCustCd())
                .custNm(custNm)
                .empNo(entity.getEmpNo())
                .whouseCd(entity.getWhouseCd())
                .projectCd(entity.getProjectCd())
                .phone(entity.getPhone())
                .outDt(outDt)
                .zipCd1(entity.getZipCd1())
                .address1(entity.getAddress1())
                .address11(entity.getAddress11())
                .shipNm(entity.getShipNm())
                .sender(entity.getSender())
                .zipCd(entity.getZipCd())
                .address2(entity.getAddress2())
                .address21(entity.getAddress21())
                .name(entity.getName())
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
