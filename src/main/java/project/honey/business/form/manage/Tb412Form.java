package project.honey.business.form.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb412;
import project.honey.comm.GlobalMethod;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tb412Form {
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
    List<Tb412_1Form> tb412_1Forms;
    
    public static Tb412 toTb412(Tb412Form form){
        String closeYn = "N";
        String prtDt = "";
        String prtEmp = "";
        if(form.getCloseYn()) closeYn = "Y";
        if(form.getPrtDt() != null) prtDt = form.getPrtDt();
        if(form.getPrtEmp() != null) prtEmp = form.getPrtEmp();
        String saleDt = GlobalMethod.replaceYmd(form.getSaleDt(), "-");
        
        return Tb412.builder()
                .saleDt(saleDt)
                .saleNo(form.getSaleNo())
                .custCd(form.getCustCd())
                .empNo(form.getEmpNo())
                .whouseCd(form.getWhouseCd())
                .saleType(form.getSaleType())
                .excgCd(form.getExcgCd())
                .outNm(form.getOutNm())
                .projectCd(form.getProjectCd())
                .zipCd(form.getZipCd())
                .address(form.getAddress())
                .address1(form.getAddress1())
                .name(form.getName())
                .takeOk(form.getTakeOk())
                .note(form.getNote())
                .note2(form.getNote2())
                .status(form.getStatus())
                .closeYn(closeYn)
                .prtEmp(prtEmp)
                .prtDt(prtDt)
                .build();
    }
}
