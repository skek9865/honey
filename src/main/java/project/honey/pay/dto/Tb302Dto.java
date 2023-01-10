package project.honey.pay.dto;

import lombok.*;
import project.honey.pay.entity.Tb302;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tb302Dto {

    private Integer seq;

    private String empNo;

    private String itemDiv;

    private String taxDiv;

    private String itemCd;

    private Integer payAmt;

    //Dto 변환 메서드
    public static Tb302Dto of(Tb302 entity) {

        return Tb302Dto.builder()
                .seq(entity.getSeq())
                .itemDiv(entity.getItemDiv())
                .taxDiv(entity.getTaxDiv())
                .itemCd(entity.getItemCd())
                .empNo(entity.getEmpNo())
                .payAmt(entity.getPayAmt().intValue())
                .build();
    }
}
