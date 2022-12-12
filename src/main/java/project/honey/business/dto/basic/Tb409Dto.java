package project.honey.business.dto.basic;

import lombok.*;
import project.honey.business.entity.basic.Tb409;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb409Dto {

    private Integer seq;
    private String excgCd;
    private String excgNm;
    private BigDecimal excgRate;
    private Boolean useYn;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public static Tb409Dto of(Tb409 entity){
        Boolean useYn = false;
        if(entity.getUseYn().equals("Y")) useYn = true;

        return Tb409Dto.builder()
                .seq(entity.getSeq())
                .excgCd(entity.getExcgCd())
                .excgNm(entity.getExcgNm())
                .excgRate(entity.getExcgRate())
                .useYn(useYn)
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
