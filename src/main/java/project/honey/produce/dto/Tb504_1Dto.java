package project.honey.produce.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb504_1Dto {

    private Integer seq;
    private Integer fkTb504;
    private String goodsCd;
    private String goodsNm;
    private String standard;
    private Integer qty;
    private Integer rQty;
    private String status;
    private String machineNo;
    private String empNo;
    private String note;

    @QueryProjection
    public Tb504_1Dto(Integer seq, Integer fkTb504, String goodsCd, String goodsNm, String standard,
                      Integer qty, Integer rQty, String status, String machineNo, String empNo, String note) {
        this.seq = seq;
        this.fkTb504 = fkTb504;
        this.goodsCd = goodsCd;
        this.goodsNm = goodsNm;
        this.standard = standard;
        this.qty = qty;
        this.rQty = rQty;
        this.status = status;
        this.machineNo = machineNo;
        this.empNo = empNo;
        this.note = note;
    }
}
