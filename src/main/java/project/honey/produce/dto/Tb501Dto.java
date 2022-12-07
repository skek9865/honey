package project.honey.produce.dto;

import lombok.*;
import project.honey.produce.entity.Tb501;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb501Dto {

    private Integer seq;

    private String productCd;

    private Integer productAl;

    private String productNm;

    public static Tb501 toTb501(Tb501Dto dto) {
        return Tb501.builder()
                .productCd(dto.getProductCd())
                .productAl(dto.getProductAl())
                .productNm(dto.getProductNm())
                .build();
    }

    public static Tb501Dto of(Tb501 entity) {
        return Tb501Dto.builder()
                .seq(entity.getSeq())
                .productCd(entity.getProductCd())
                .productAl(entity.getProductAl())
                .productNm(entity.getProductNm())
                .build();
    }
}
