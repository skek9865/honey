package project.honey.business.form.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb415;
import project.honey.comm.GlobalMethod;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tb415Form {
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
    List<Tb415_1Form> tb415_1Forms;

    public static Tb415 toTb415(Tb415Form form){
        String closeYn = "N";
        String prtDt = "";
        String prtEmp = "";
        if(form.getCloseYn()) closeYn = "Y";
        if(form.getPrtDt() != null) prtDt = form.getPrtDt();
        if(form.getPrtEmp() != null) prtEmp = form.getPrtEmp();
        String orderDt = GlobalMethod.replaceYmd(form.getOrderDt(), "-");
        String deadDt = GlobalMethod.replaceYmd(form.getDeadDt(), "-");

        return Tb415.builder()
                .orderDt(orderDt)
                .orderNo(form.getOrderNo())
                .custCd(form.getCustCd())
                .empNo(form.getEmpNo())
                .whouseCd(form.getWhouseCd())
                .saleType(form.getSaleType())
                .excgCd(form.getExcgCd())
                .note(form.getNote())
                .projectCd(form.getProjectCd())
                .deadDt(deadDt)
                .status(form.getStatus())
                .closeYn(closeYn)
                .prtEmp(prtEmp)
                .prtDt(prtDt)
                .build();
    }
}
