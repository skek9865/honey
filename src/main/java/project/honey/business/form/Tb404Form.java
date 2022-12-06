package project.honey.business.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.honey.business.entity.Tb401;
import project.honey.business.entity.Tb404;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb404Form {

    private Integer seq;
    private String classSeq;
    private String classCd;
    private Integer classAl;
    private String classNm;

    public static Tb404 toTb404(Tb404Form form){
        return Tb404.builder()
                .classSeq(form.getClassSeq())
                .classCd(form.getClassCd())
                .classAl(form.getClassAl())
                .classNm(form.getClassNm())
                .build();
    }
}
