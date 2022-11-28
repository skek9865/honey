package project.honey.pay.dto;

import lombok.*;
import project.honey.pay.entity.Tb302;
import project.honey.pay.entity.Tb303;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tb303Dto {

    private Integer seq;

    private String empNo;

    private String itemDiv;

    private String itemCd;

    private Integer payAmt;

    private String payDt;

    private String rPayDt;

    //Dto 변환 메서드
    public static Tb303Dto of(Tb303 entity) {

        return Tb303Dto.builder()
                .seq(entity.getSeq())
                .itemDiv(entity.getItemDiv())
                .itemCd(entity.getItemCd())
                .empNo(entity.getEmpNo())
                .payAmt(entity.getPayAmt().intValue())
                .payDt(entity.getPayDt())
                .rPayDt(entity.getRPayDt())
                .build();
    }
}
