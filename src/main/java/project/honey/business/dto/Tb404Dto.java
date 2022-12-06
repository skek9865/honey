package project.honey.business.dto;

import lombok.*;
import project.honey.business.entity.Tb401;
import project.honey.business.entity.Tb404;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb404Dto {

    private Integer seq;
    private String classSeq;
    private String classCd;
    private Integer classAl;
    private String classNm;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public static Tb404Dto of(Tb404 entity, Map<String, String> map){

        return Tb404Dto.builder()
                .seq(entity.getSeq())
                .classSeq(map.get(entity.getClassSeq()))
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
