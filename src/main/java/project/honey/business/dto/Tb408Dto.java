package project.honey.business.dto;

import lombok.*;
import project.honey.business.entity.Tb408;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb408Dto {

    private Integer seq;
    private String projectCd;
    private Integer projectAl;
    private String projectNm;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public static Tb408Dto of(Tb408 entity){
        return Tb408Dto.builder()
                .seq(entity.getSeq())
                .projectCd(entity.getProjectCd())
                .projectAl(entity.getProjectAl())
                .projectNm(entity.getProjectNm())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
