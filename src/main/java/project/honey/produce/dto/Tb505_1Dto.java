package project.honey.produce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import project.honey.produce.entity.Tb504;
import project.honey.produce.entity.Tb504_1;
import project.honey.produce.entity.Tb505;
import project.honey.produce.entity.Tb505_1;

@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb505_1Dto {

    private Integer seq;
    private Integer fkTb505;
    private String goodsCd;
    private String goodsNm;
    private Integer qty;
    private Integer oQty;
    private String note;

    @QueryProjection
    public Tb505_1Dto(Integer seq, Integer fkTb505, String goodsCd, String goodsNm, Integer qty, Integer oQty, String note) {
        this.seq = seq;
        this.fkTb505 = fkTb505;
        this.goodsCd = goodsCd;
        this.goodsNm = goodsNm;
        this.qty = qty;
        this.oQty = oQty;
        this.note = note;
    }

    public static Tb505_1 toTb505_1(Tb505_1Dto dto, Tb505 tb505) {
        return Tb505_1.builder()
                .tb505(tb505)
                .goodsCd(dto.getGoodsCd())
                .qty(dto.getQty())
                .oQty(dto.getOQty())
                .note(dto.getNote())
                .build();
    }
}
