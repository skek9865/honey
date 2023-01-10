package project.honey.personDepart.dto;

import lombok.*;
import project.honey.personDepart.entity.Tb202;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb202Dto {

    private Integer seq;
    private String deptCd;
    private String deptNm;
    private Boolean useYn;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public static Tb202Dto of(Tb202 entity) {
        Boolean useYn = false;
        if(entity.getUseYn().equals("Y")) useYn = true;

        return Tb202Dto.builder()
                .seq(entity.getSeq())
                .deptCd(entity.getDeptCd())
                .deptNm(entity.getDeptNm())
                .useYn(useYn)
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
