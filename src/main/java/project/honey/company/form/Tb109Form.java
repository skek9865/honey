package project.honey.company.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.honey.company.entity.Tb109;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb109Form {

    private Integer seq;
    private Integer tb101;
    private String company;
    private String telNo;
    private String mobile;
    private String fax;
    private String email;
    private String etcTel1;
    private String etcTel2;
    private String etcTel3;
    private String note;

    public static Tb109 toTb109(Tb109Form form){
        return Tb109.builder()
                .tb101(form.getTb101())
                .company(form.getCompany())
                .telNo(form.getTelNo())
                .mobile(form.getMobile())
                .fax(form.getFax())
                .email(form.getEmail())
                .etcTel1(form.getEtcTel1())
                .etcTel2(form.getEtcTel2())
                .etcTel3(form.getEtcTel3())
                .note(form.getNote())
                .build();
    }
}
