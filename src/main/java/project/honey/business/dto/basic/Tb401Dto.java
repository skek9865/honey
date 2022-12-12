package project.honey.business.dto.basic;

import lombok.*;
import project.honey.business.entity.basic.Tb401;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb401Dto {

    private Integer seq;
    private String classSeq;
    private String classCd;
    private Integer classAl;
    private String classNm;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public static Tb401Dto of(Tb401 entity, String classSeqNm){

        if (classSeqNm == null) classSeqNm = entity.getClassSeq();

        return Tb401Dto.builder()
                .seq(entity.getSeq())
                .classSeq(classSeqNm)
                .classCd(entity.getClassCd())
                .classAl(entity.getClassAl())
                .classNm(entity.getClassNm())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
