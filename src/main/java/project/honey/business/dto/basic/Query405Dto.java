package project.honey.business.dto.basic;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Query405Dto {

    private Integer seq;
    private String classCd;
    private String goodsNm;
    private String standard;
    private Integer qty;

    @QueryProjection
    public Query405Dto(Integer seq, String standard, String classCd, String goodsNm, Integer qty) {
        this.seq = seq;
        this.standard = standard;
        this.classCd = classCd;
        this.goodsNm = goodsNm;
        this.qty = qty;
    }
}
