package project.honey.company.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.honey.comm.GlobalMethod;
import project.honey.company.entity.Tb107;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb107Form {
    private Integer seq;
    private Integer tb101;
    private String carNm;
    private String carYear;
    private String carNo;
    private String empNo;
    private String instNm;
    private String instSDt;
    private String instEDt;
    private String ageLimit;
    private Integer instAmt;
    private String note;

    public static Tb107 toTb107(Tb107Form form){
        String instSDt = GlobalMethod.replaceYmd(form.getInstSDt(), "-");
        String instEDt = GlobalMethod.replaceYmd(form.getInstEDt(), "-");

        return Tb107.builder()
                .tb101(form.getTb101())
                .carNm(form.getCarNm())
                .carNo(form.getCarNo())
                .carYear(form.getCarYear())
                .empNo(form.getEmpNo())
                .instNm(form.getInstNm())
                .instSDt(instSDt)
                .instEDt(instEDt)
                .ageLimit(form.getAgeLimit())
                .instAmt(form.getInstAmt())
                .note(form.getNote())
                .build();
    }
}
