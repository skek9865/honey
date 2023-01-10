package project.honey.business.form.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb410;
import project.honey.comm.GlobalMethod;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tb410Form {
    private Integer seqP;
    private String estimDt;
    private Integer estimNo;
    private String custCd;
    private String empNo;
    private String whouseCd;
    private String saleType;
    private String excgCd;
    private String noteP;
    private String payCondit;
    private String name;
    private String expDt;
    private String phone;
    private String email;
    private String note2;
    private String status;
    private Boolean closeYn;
    private String prtEmp;
    private String prtDt;
    List<Tb410_1Form> tb410_1Forms;

    public static Tb410 toTb410(Tb410Form form){
        String closeYn = "N";
        if(form.getCloseYn()) closeYn = "Y";
        String estDt = GlobalMethod.replaceYmd(form.getEstimDt(), "-");
        return Tb410.builder()
                .estimDt(estDt)
                .estimNo(form.getEstimNo())
                .custCd(form.getCustCd())
                .empNo(form.getEmpNo())
                .whouseCd(form.getWhouseCd())
                .saleType(form.getSaleType())
                .excgCd(form.getExcgCd())
                .note(form.getNoteP())
                .payCondit(form.getPayCondit())
                .name(form.getName())
                .expDt(form.getExpDt())
                .phone(form.getPhone())
                .email(form.getEmail())
                .note2(form.getNote2())
                .status(form.getStatus())
                .closeYn(closeYn)
                .prtEmp(form.getPrtEmp())
                .prtDt(form.getPrtDt())
                .build();
    }
}
