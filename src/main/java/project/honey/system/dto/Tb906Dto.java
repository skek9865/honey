package project.honey.system.dto;

import lombok.*;
import project.honey.system.entity.Tb906;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Tb906Dto {

    private Integer seq;
    private String fstId;
    private String scdId;
    private Integer alien;
    private String codeNm;

    //검색용
    private String sfstid;

    // Entity 변환 메서드
    public static Tb906 toTb906(Tb906Dto dto) {

        return Tb906.builder()
                .seq(dto.getSeq())
                .fstId(dto.getFstId())
                .scdId(dto.getScdId())
                .alien(dto.getAlien())
                .codeNm(dto.getCodeNm())
                .build();
    }

    //Dto 변환 메서드
    public static Tb906Dto of(Tb906 entity) {

        return Tb906Dto.builder()
                .seq(entity.getSeq())
                .fstId(entity.getFstId())
                .scdId(entity.getScdId())
                .alien(entity.getAlien())
                .codeNm(entity.getCodeNm())
                .build();
    }
}
