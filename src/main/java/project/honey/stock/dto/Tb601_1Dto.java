package project.honey.stock.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import project.honey.stock.entity.Tb601;
import project.honey.stock.entity.Tb601_1;

@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb601_1Dto {

    private Integer seq;
    private Integer fkTb601;
    private String goodsCd;
    private String goodsNm;
    private Integer qty;
    private Integer rQty;
    private String note;

    @QueryProjection
    public Tb601_1Dto(Integer seq, Integer fkTb601, String goodsCd, String goodsNm, Integer qty, Integer rQty, String note) {
        this.seq = seq;
        this.fkTb601 = fkTb601;
        this.goodsCd = goodsCd;
        this.goodsNm = goodsNm;
        this.qty = qty;
        this.rQty = rQty;
        this.note = note;
    }

    public static Tb601_1 toTb601_1(Tb601_1Dto dto, Tb601 tb601) {
        return Tb601_1.builder()
                .tb601(tb601)
                .goodsCd(dto.getGoodsCd())
                .qty(dto.getQty())
                .rQty(dto.getRQty())
                .note(dto.getNote())
                .build();
    }
}
