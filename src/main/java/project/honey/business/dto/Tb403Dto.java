package project.honey.business.dto;

import lombok.*;
import project.honey.business.entity.Tb403;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb403Dto {

    private Integer seq;
    private String whouseCla;
    private String productCd;
    private String whouseCd;
    private Integer whouseAl;
    private String whouseNm;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public static Tb403Dto of (Tb403 entity, String whouseClaNm){

        if(whouseClaNm == null) whouseClaNm = entity.getWhouseCla();

        return Tb403Dto.builder()
                .seq(entity.getSeq())
                .whouseCla(whouseClaNm)
                .productCd(entity.getProductCd())
                .whouseCd(entity.getWhouseCd())
                .whouseAl(entity.getWhouseAl())
                .whouseNm(entity.getWhouseNm())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
