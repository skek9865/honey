package project.honey.business.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.honey.business.entity.Tb406;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb406Form {

    private Integer seq;
    private String specialCd;
    private Integer specialAl;
    private String specialNm;

    public static Tb406 toTb406(Tb406Form form){
        return Tb406.builder()
                .specialCd(form.getSpecialCd())
                .specialAl(form.getSpecialAl())
                .specialNm(form.getSpecialNm())
                .build();
    }
}
