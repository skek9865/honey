package project.honey.personDepart.dto;

import lombok.*;
import project.honey.personDepart.entity.Tb203;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb203Dto {

    private Integer seq;
    private String empNo;
    private String part;
    private String saveFNm;
    private String outFNm;
    private String note;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb203Dto(String empNo){
        this.empNo = empNo;
    }

    public static Tb203Dto of(Tb203 entity){
        return Tb203Dto.builder()
                .seq(entity.getSeq())
                .empNo(entity.getEmpNo())
                .saveFNm(entity.getSaveFNm())
                .outFNm(entity.getOutFNm())
                .note(entity.getNote())
                .part(entity.getPart())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
