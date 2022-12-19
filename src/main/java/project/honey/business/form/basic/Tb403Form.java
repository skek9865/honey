package project.honey.business.form.basic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import project.honey.business.entity.basic.Tb403;

@AllArgsConstructor
@Builder
@Getter
@ToString
public class Tb403Form {

    private Integer seq;
    private String whouseCla;
    private String productCd;
    private String whouseCd;
    private Integer whouseAl;
    private String whouseNm;

    public static Tb403 toTb403(Tb403Form form){
        return Tb403.builder()
                .whouseCla(form.whouseCla)
                .productCd(form.getProductCd())
                .whouseCd(form.whouseCd)
                .whouseAl(form.whouseAl)
                .whouseNm(form.whouseNm)
                .build();
    }
}
