package project.honey.personDepart.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import project.honey.personDepart.entity.Tb202;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class Tb202Form {

    private Integer seq;

    @NotNull(message = "부서코드는 필수 입력 값 입니다")
    @Range(max = 5, message = "부서코드는 5자리 이하로 입력해주세요")
    private String deptCd;

    @NotNull
    @Range(max = 30)
    private String deptNm;

    @NotNull
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
