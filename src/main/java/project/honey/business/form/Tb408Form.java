package project.honey.business.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.honey.business.entity.Tb408;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb408Form {

    private Integer seq;
    private String projectCd;
    private Integer projectAl;
    private String projectNm;

    public static Tb408 toTb408(Tb408Form form){
        return Tb408.builder()
                .projectCd(form.getProjectCd())
                .projectAl(form.getProjectAl())
                .projectNm(form.getProjectNm())
                .build();
    }
}
