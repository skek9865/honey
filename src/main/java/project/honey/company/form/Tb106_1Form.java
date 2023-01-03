package project.honey.company.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.honey.comm.GlobalMethod;
import project.honey.company.entity.Tb106_1;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb106_1Form {
    private Integer seq;
    private Integer tb101;
    private Integer tb106;
    private String dvStDt;
    private String dvEdDt;
    private String empNo;
    private Integer price;
    private Integer penalty;
    private String place;
    private String purpose;
    private String note;

    public static Tb106_1 toTb106_1(Tb106_1Form form){
        String dvStDt = GlobalMethod.replaceYmd(form.getDvStDt(), "-");
        String dvEdDt = GlobalMethod.replaceYmd(form.getDvEdDt(), "-");

        return Tb106_1.builder()
                .tb101(form.getTb101())
                .tb106(form.getTb106())
                .dvStDt(dvStDt)
                .dvEdDt(dvEdDt)
                .empNo(form.getEmpNo())
                .price(form.getPrice())
                .penalty(form.getPenalty())
                .place(form.getPlace())
                .purpose(form.getPurpose())
                .note(form.getNote())
                .build();
    }
}
