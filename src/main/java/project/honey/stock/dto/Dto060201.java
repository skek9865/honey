package project.honey.stock.dto;

import lombok.*;
import project.honey.business.dto.basic.Query405Dto;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Dto060201 {

    private Integer seq;
    private String classNm;
    private String goodsNm;
    private String standard;
    private Integer qty;

    public static Dto060201 of(Query405Dto dto, Map<String, String> classMap) {
        return Dto060201.builder()
                .seq(dto.getSeq())
                .classNm(classMap.get(dto.getClassCd()))
                .goodsNm(dto.getGoodsNm())
                .standard(dto.getStandard())
                .qty(dto.getQty())
                .build();
    }
}
