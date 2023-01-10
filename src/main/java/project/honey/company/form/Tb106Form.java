package project.honey.company.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.honey.comm.GlobalMethod;
import project.honey.company.entity.Tb106;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb106Form {
    private Integer seq;
    private Integer tb101;
    private String carNm;
    private String carYear;
    private String carNo;
    private String empNo;
    private String instNm;
    private String buyDt;
    private Integer buyAmt;
    private String note;

    public static Tb106 toTb106(Tb106Form form){
        String buyDt = GlobalMethod.replaceYmd(form.getBuyDt(), "-");

        return Tb106.builder()
                .tb101(form.getTb101())
                .carNm(form.getCarNm())
                .carYear(form.getCarYear())
                .carNo(form.getCarNo())
                .empNo(form.getEmpNo())
                .instNm(form.getInstNm())
                .buyDt(buyDt)
                .buyAmt(form.getBuyAmt())
                .note(form.getNote())
                .build();
    }
}
