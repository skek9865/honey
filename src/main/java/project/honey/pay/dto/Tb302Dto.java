package project.honey.pay.dto;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import project.honey.pay.entity.Tb301;
import project.honey.pay.entity.Tb302;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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
