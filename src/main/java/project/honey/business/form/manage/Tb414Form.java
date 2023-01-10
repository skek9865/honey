package project.honey.business.form.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb414;
import project.honey.comm.GlobalMethod;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tb414Form {
    private Integer seq;
    private String shipDt;
    private Integer shipNo;
    private String custCd;
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
    List<Tb414_1Form> tb414_1Forms;

    public static Tb414 toTb414(Tb414Form form){
        String closeYn = "N";
        String prtDt = "";
        String prtEmp = "";
        if(form.getCloseYn()) closeYn = "Y";
        if(form.getPrtDt() != null) prtDt = form.getPrtDt();
        if(form.getPrtEmp() != null) prtEmp = form.getPrtEmp();
        String shipDt = GlobalMethod.replaceYmd(form.getShipDt(), "-");

        return Tb414.builder()
                .shipDt(shipDt)
                .shipNo(form.getShipNo())
                .custCd(form.getCustCd())
                .empNo(form.getEmpNo())
                .whouseCd(form.getWhouseCd())
                .projectCd(form.getProjectCd())
                .phone(form.getPhone())
                .zipCd(form.getZipCd())
                .address(form.getAddress())
                .address1(form.getAddress1())
                .note(form.getNote())
                .status(form.getStatus())
                .closeYn(closeYn)
                .prtEmp(prtEmp)
                .prtDt(prtDt)
                .build();
    }

}
