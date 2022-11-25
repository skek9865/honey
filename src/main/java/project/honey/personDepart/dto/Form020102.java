package project.honey.personDepart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.honey.personDepart.entity.Tb202;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class Form020102 {

    private Integer seq;
    private String deptCd;
    private String deptNm;
    private Boolean useYn;

    public static Tb202 toTb202(Form020102 form) {
        String useYn = "N";
        if(form.getUseYn()) useYn = "Y";

        return Tb202.builder()
                .deptCd(form.getDeptCd())
                .deptNm(form.getDeptNm())
                .useYn(useYn)
                .build();
    }
}
