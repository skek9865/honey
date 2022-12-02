package project.honey.system.dto;

import lombok.*;
import project.honey.system.entity.Tb904;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb904Dto {

    private Integer seq;

    private String fstId;

    private String scdId;

    private String thdId;

    private Integer alien;

    private String menuNm;

    private String menuUrl;

    private String useYn;

    public void setUseYn(String useYn) {
        this.useYn = useYn != null ? useYn : "N";
    }

    public static Tb904 toTb904(Tb904Dto dto) {
        return Tb904.builder()
                .fstId(dto.getFstId())
                .scdId(dto.getScdId())
                .thdId(dto.getThdId())
                .alien(dto.getAlien())
                .menuNm(dto.getMenuNm())
                .menuUrl(dto.getMenuUrl())
                .useYn(dto.getUseYn())
                .build();
    }

    public static Tb904Dto of(Tb904 entity) {
        return Tb904Dto.builder()
                .seq(entity.getSeq())
                .fstId(entity.getFstId())
                .scdId(entity.getScdId())
                .thdId(entity.getThdId())
                .alien(entity.getAlien())
                .menuNm(entity.getMenuNm())
                .menuUrl(entity.getMenuUrl())
                .useYn(entity.getUseYn())
                .build();
    }
}
