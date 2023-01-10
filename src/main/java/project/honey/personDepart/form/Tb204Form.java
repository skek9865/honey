package project.honey.personDepart.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import project.honey.personDepart.entity.Tb204;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class Tb204Form {

    private Integer seq;
    private Integer compNo;
    private String part;
    private MultipartFile file;
    private String note;

    public static Tb204 toTb204(Tb204Form form, String saveNm, String outNm){
        return Tb204.builder()
                .compNo(form.getCompNo())
                .part(form.getPart())
                .saveFNm(saveNm)
                .outFNm(outNm)
                .note(form.getNote())
                .build();
    }
}
