package project.honey.produce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Query506_1Dto {

    private Integer seq;
    private String wHouseIn;
    private String goodsCd;
    private Integer qty;

    @QueryProjection
    public Query506_1Dto(Integer seq, String wHouseIn, String goodsCd, Integer qty) {
        this.seq = seq;
        this.wHouseIn = wHouseIn;
        this.goodsCd = goodsCd;
        this.qty = qty;
    }
}
