package project.honey.pay.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import project.honey.pay.entity.Tb301;
import project.honey.system.dto.CodeDto;

import java.util.List;

@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tb301Dto {

    private Integer seq;

    private String itemDiv;

    private String taxDiv;

    private Integer itemCd;

    private String itemNm;

    private Double taxRate;

    private String useYn;

    @QueryProjection
    public Tb301Dto(Integer seq, String itemDiv, String taxDiv, Integer itemCd, String itemNm, Double taxRate, String useYn) {
        this.seq = seq;
        this.itemDiv = itemDiv;
        this.taxDiv = taxDiv;
        this.itemCd = itemCd;
        this.itemNm = itemNm;
        this.taxRate = taxRate;
        this.useYn = useYn;
    }

    //임시 기본값 설정
    public void setTaxDiv(String taxDiv) {
        this.taxDiv = taxDiv!=null?taxDiv:"N";
    }
    public void setUseYn(String useYn) {
        this.useYn = useYn!=null?useYn:"N";
    }

    // Entity 변환 메서드
    public static Tb301 toTb301(Tb301Dto dto) {

        return Tb301.builder()
                .seq(dto.getSeq())
                .itemDiv(dto.getItemDiv())
                .taxDiv(dto.getTaxDiv())
                .itemCd(dto.getItemCd())
                .itemNm(dto.getItemNm())
                .taxRate(dto.getTaxRate())
                .useYn(dto.getUseYn())
                .build();
    }

    //Dto 변환 메서드
    public static Tb301Dto of(Tb301 entity) {

        return Tb301Dto.builder()
                .seq(entity.getSeq())
                .itemDiv(entity.getItemDiv())
                .taxDiv(entity.getTaxDiv())
                .itemCd(entity.getItemCd())
                .itemNm(entity.getItemNm())
                .taxRate(entity.getTaxRate())
                .useYn(entity.getUseYn())
                .build();
    }
}
