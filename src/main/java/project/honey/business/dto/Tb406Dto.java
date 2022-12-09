package project.honey.business.dto;

import lombok.*;
import project.honey.business.entity.Tb406;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb406Dto {

    private Integer seq;
    private String specialCd;
    private Integer specialAl;
    private String specialNm;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public static Tb406Dto of(Tb406 entity){
        return Tb406Dto.builder()
                .seq(entity.getSeq())
                .specialCd(entity.getSpecialCd())
                .specialAl(entity.getSpecialAl())
                .specialNm(entity.getSpecialNm())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
