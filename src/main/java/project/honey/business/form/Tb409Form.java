package project.honey.business.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.honey.business.entity.Tb409;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb409Form {

    private Integer seq;
    private String excgCd;
    private String excgNm;
    private BigDecimal excgRate;
    private Boolean useYn;

    public static Tb409 toTb409(Tb409Form form){
        String useYn = "N";
        if(form.getUseYn()) useYn = "Y";

        return Tb409.builder()
                .excgCd(form.getExcgCd())
                .excgNm(form.getExcgNm())
                .excgRate(form.getExcgRate())
                .useYn(useYn)
                .build();
    }
}
