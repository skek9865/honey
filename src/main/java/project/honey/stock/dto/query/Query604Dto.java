package project.honey.stock.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Query604Dto {

    private Integer seq;
    private String wHouseDt;
    private Integer wHouseNo;
    private String goodsNm;
    private String wHouseOut;
    private Integer stQty;
    private Integer reQty;
    private Integer adQty;
    private Integer wPrice;
    private String note;

    @QueryProjection
    public Query604Dto(Integer seq,String wHouseDt, Integer wHouseNo, String goodsNm, String wHouseOut, Integer stQty, Integer reQty, Integer adQty, Integer wPrice, String note) {
        this.seq = seq;
        this.wHouseDt = wHouseDt;
        this.wHouseNo = wHouseNo;
        this.goodsNm = goodsNm;
        this.wHouseOut = wHouseOut;
        this.stQty = stQty;
        this.reQty = reQty;
        this.adQty = adQty;
        this.wPrice = wPrice;
        this.note = note;
    }
}
