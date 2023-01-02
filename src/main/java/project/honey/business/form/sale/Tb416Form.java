package project.honey.business.form.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb416;
import project.honey.comm.GlobalMethod;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tb416Form {

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
    List<Tb416_1Form> tb416_1Forms;

    public static Tb416 toTb416(Tb416Form form){
        String closeYn = "N";
        String prtDt = "";
        String prtEmp = "";
        if(form.getCloseYn()) closeYn = "Y";
        if(form.getPrtDt() != null) prtDt = form.getPrtDt();
        if(form.getPrtEmp() != null) prtEmp = form.getPrtEmp();
        String buyDt = GlobalMethod.replaceYmd(form.getBuyDt(), "-");

        return Tb416.builder()
                .buyDt(buyDt)
                .buyNo(form.getBuyNo())
                .custCd(form.getCustCd())
                .empNo(form.getEmpNo())
                .whouseCd(form.getWhouseCd())
                .saleType(form.getSaleType())
                .excgCd(form.getExcgCd())
                .note(form.getNote())
                .projectCd(form.getProjectCd())
                .status(form.getStatus())
                .closeYn(closeYn)
                .prtEmp(prtEmp)
                .prtDt(prtDt)
                .build();
    }
}
