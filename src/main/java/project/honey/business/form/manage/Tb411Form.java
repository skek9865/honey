package project.honey.business.form.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb411;
import project.honey.comm.GlobalMethod;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tb411Form {

    private Integer seq;
    private String orderDt;
    private Integer orderNo;
    private String custCd;
    private String empNo;
    private String whouseCd;
    private String saleType;
    private String excgCd;
    private String projectCd;
    private String note;
    private String payCondit;
    private String expDt;
    private String deadDt;
    private String note2;
    private String status;
    private Boolean closeYn;
    private String prtEmp;
    private String prtDt;
    List<Tb411_1Form> tb411_1Forms;

    public static Tb411 toTb411(Tb411Form form){
        String closeYn = "N";
        String prtDt = "";
        String prtEmp = "";
        if(form.getCloseYn()) closeYn = "Y";
        if(form.getPrtDt() != null) prtDt = form.getPrtDt();
        if(form.getPrtEmp() != null) prtEmp = form.getPrtEmp();
        String orderDt = GlobalMethod.replaceYmd(form.getOrderDt(), "-");
        String deadDt = GlobalMethod.replaceYmd(form.getDeadDt(), "-");

        return Tb411.builder()
                .orderDt(orderDt)
                .orderNo(form.getOrderNo())
                .custCd(form.getCustCd())
                .empNo(form.getEmpNo())
                .whouseCd(form.getWhouseCd())
                .saleType(form.getSaleType())
                .excgCd(form.getExcgCd())
                .projectCd(form.getProjectCd())
                .note(form.getNote())
                .payCondit(form.getPayCondit())
                .expDt(form.getExpDt())
                .deadDt(deadDt)
                .note2(form.getNote2())
                .status(form.getStatus())
                .closeYn(closeYn)
                .prtDt(prtDt)
                .prtEmp(prtEmp)
                .build();
    }
}
