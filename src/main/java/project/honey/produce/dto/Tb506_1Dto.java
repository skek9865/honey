package project.honey.produce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import project.honey.produce.entity.Tb505;
import project.honey.produce.entity.Tb505_1;
import project.honey.produce.entity.Tb506;
import project.honey.produce.entity.Tb506_1;

@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb506_1Dto {

    private Integer seq;
    private Integer fkTb506;
    private String goodsCd;
    private String goodsNm;
    private Integer qty;
    private Integer wPrice;
    private String standard;
    private String note;

    @QueryProjection
    public Tb506_1Dto(Integer seq, Integer fkTb506, String goodsCd, String goodsNm, Integer qty, Integer wPrice, String standard, String note) {
        this.seq = seq;
        this.fkTb506 = fkTb506;
        this.goodsCd = goodsCd;
        this.goodsNm = goodsNm;
        this.qty = qty;
        this.wPrice = wPrice;
        this.standard = standard;
        this.note = note;
    }

    public static Tb506_1 toTb506_1(Tb506_1Dto dto, Tb506 tb506) {
        return Tb506_1.builder()
                .tb506(tb506)
                .goodsCd(dto.getGoodsCd())
                .qty(dto.getQty())
                .standard(dto.getStandard())
                .note(dto.getNote())
                .build();
    }
}
