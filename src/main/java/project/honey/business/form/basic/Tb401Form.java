package project.honey.business.form.basic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.honey.business.entity.basic.Tb401;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb401Form {

    private Integer seq;
    private String classSeq;
    private String classCd;
    private Integer classAl;
    private String classNm;

    public static Tb401 toTb401(Tb401Form form){
        return Tb401.builder()
                .classSeq(form.getClassSeq())
                .classCd(form.getClassCd())
                .classAl(form.getClassAl())
                .classNm(form.getClassNm())
                .build();
    }
}
