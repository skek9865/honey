package project.honey.personDepart.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.honey.personDepart.entity.Tb202;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class Tb202Form {

    private Integer seq;
    private String deptCd;
    private String deptNm;
    private Boolean useYn;

    public static Tb202 toTb202(Tb202Form form) {
        String useYn = "N";
        if(form.getUseYn()) useYn = "Y";

        return Tb202.builder()
                .deptCd(form.getDeptCd())
                .deptNm(form.getDeptNm())
                .useYn(useYn)
                .build();
    }
}
