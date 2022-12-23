package project.honey.business.form.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb413;
import project.honey.comm.GlobalMethod;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tb413Form {
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
    List<Tb413_1Form> tb413_1Forms;

    public static Tb413 toTb413(Tb413Form form){
        String closeYn = "N";
        String prtDt = "";
        String prtEmp = "";
        if(form.getCloseYn()) closeYn = "Y";
        if(form.getPrtDt() != null) prtDt = form.getPrtDt();
        if(form.getPrtEmp() != null) prtEmp = form.getPrtEmp();
        String shipDt = GlobalMethod.replaceYmd(form.getShipDt(), "-");
        String outDt = GlobalMethod.replaceYmd(form.getOutDt(), "-");

        return Tb413.builder()
                .shipDt(shipDt)
                .shipNo(form.getShipNo())
                .custCd(form.getCustCd())
                .empNo(form.getEmpNo())
                .whouseCd(form.getWhouseCd())
                .projectCd(form.getProjectCd())
                .phone(form.getPhone())
                .outDt(outDt)
                .zipCd1(form.getZipCd1())
                .address1(form.getAddress1())
                .address11(form.getAddress11())
                .shipNm(form.getShipNm())
                .sender(form.getSender())
                .zipCd(form.getZipCd())
                .address2(form.getAddress2())
                .address21(form.getAddress21())
                .name(form.getName())
                .note(form.getNote())
                .note2(form.getNote2())
                .status(form.getStatus())
                .closeYn(closeYn)
                .prtEmp(prtEmp)
                .prtDt(prtDt)
                .build();
    }
}
