package project.honey.business.form.manage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.honey.business.entity.manage.Tb410;

@AllArgsConstructor
@Builder
@Getter
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
    private String closeYn;
    private String prtEmp;
    private String prtDt;

    public static Tb410 toTb410(Tb410Form form){
        return Tb410.builder()
                .estimDt(form.getEstimDt())
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
                .closeYn(form.getCloseYn())
                .prtEmp(form.getPrtEmp())
                .prtDt(form.getPrtDt())
                .build();
    }
}
