package project.honey.business.form.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb417;
import project.honey.comm.GlobalMethod;
import project.honey.company.entity.Tb102;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb417Form {
    private Integer seq;
    private String amountDt;
    private Integer amountNo;
    private String amountCl;
    private String amountTy;
    private Integer tb102;
    private Integer price;
    private Integer amount;
    private String custCd;
    private String empNo;
    private String note;
    private String note1;

    public static Tb417 toTb417(Tb417Form form, Tb102 tb102){
        String amountDt = GlobalMethod.replaceYmd(form.getAmountDt(), "-");

        return Tb417.builder()
                .amountDt(amountDt)
                .amountNo(form.getAmountNo())
                .amountCl(form.getAmountCl())
                .amountTy(form.getAmountTy())
                .tb102(tb102)
                .price(form.getPrice())
                .amount(form.getAmount())
                .custCd(form.getCustCd())
                .empNo(form.getEmpNo())
                .note(form.getNote())
                .note1(form.getNote1())
                .build();
    }
}
