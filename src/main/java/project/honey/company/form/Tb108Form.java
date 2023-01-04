package project.honey.company.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import project.honey.comm.GlobalMethod;
import project.honey.company.entity.Tb108;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb108Form {

    private Integer seq;
    private Integer tb101;
    private String part;
    private String patentNo;
    private String patentApNo;
    private String patentDt;
    private String regDt;
    private String patentNm;
    private String patentMan;
    private String inventor;
    private String issueDBy;
    private String note;
    private MultipartFile img;

    public static Tb108 toTb108(Tb108Form form, String picNm){
        String patentDt = GlobalMethod.replaceYmd(form.getPatentDt(), "-");
        String regDt = GlobalMethod.replaceYmd(form.getRegDt(), "-");

        return Tb108.builder()
                .tb101(form.getTb101())
                .part(form.getPart())
                .patentNo(form.getPatentNo())
                .patentApNo(form.getPatentApNo())
                .patentDt(patentDt)
                .regDt(regDt)
                .patentNm(form.getPatentNm())
                .patentMan(form.getPatentMan())
                .inventor(form.getInventor())
                .issueDBy(form.getIssueDBy())
                .note(form.getNote())
                .picNm(picNm)
                .build();
    }
}
