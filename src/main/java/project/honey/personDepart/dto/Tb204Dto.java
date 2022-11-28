package project.honey.personDepart.dto;

import lombok.*;
import project.honey.personDepart.entity.Tb204;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb204Dto {

    private Integer seq;
    private Integer compNo;
    private String part;
    private String saveFNm;
    private String outFNm;
    private String note;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb204Dto(Integer compNo){
        this.compNo = compNo;
    }

    public static Tb204Dto of(Tb204 entity){
        return Tb204Dto.builder()
                .seq(entity.getSeq())
                .compNo(entity.getCompNo())
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
