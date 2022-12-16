package project.honey.stock.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import project.honey.stock.entity.Tb602;
import project.honey.stock.entity.Tb602_1;

@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb602_1Dto {

    private Integer seq;
    private Integer fkTb601;
    private String goodsCd;
    private String goodsNm;
    private Integer qty;
    private String useType;
    private String note;

    @QueryProjection
    public Tb602_1Dto(Integer seq, Integer fkTb601, String goodsCd, String goodsNm, Integer qty, String useType, String note) {
        this.seq = seq;
        this.fkTb601 = fkTb601;
        this.goodsCd = goodsCd;
        this.goodsNm = goodsNm;
        this.qty = qty;
        this.useType = useType;
        this.note = note;
    }

    public static Tb602_1 toTb602_1(Tb602_1Dto dto, Tb602 tb602) {
        return Tb602_1.builder()
                .tb602(tb602)
                .goodsCd(dto.getGoodsCd())
                .qty(dto.getQty())
                .useType(dto.getUseType())
                .note(dto.getNote())
                .build();
    }
}
