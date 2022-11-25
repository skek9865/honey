package project.honey.personDepart.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import project.honey.personDepart.entity.Tb203;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class Tb203Form {

    private Integer seq;
    private String empNo;
    private String part;
    private MultipartFile file;
    private String note;

    public static Tb203 toTb203(Tb203Form form, String saveNm, String outNm){
        return Tb203.builder()
                .empNo(form.getEmpNo())
                .part(form.getPart())
                .saveFNm(saveNm)
                .outFNm(outNm)
                .note(form.getNote())
                .build();
    }
}
