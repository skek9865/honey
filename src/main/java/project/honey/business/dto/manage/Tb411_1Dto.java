package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb411;
import project.honey.business.entity.manage.Tb411_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb411_1Dto {

    private Integer seq;
    private Tb411 tb411;
    private String goodsCd;
    private String standard;
    private Integer qty;
    private Integer price;
    private Integer amt;
    private Integer vat;
    private String deadDt;
    private String note;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public static Tb411_1Dto of(Tb411_1 entity){
        Tb411 tb411 = Tb411.builder().seq(entity.getSeq()).build();

        return Tb411_1Dto.builder()
                .seq(entity.getSeq())
                .tb411(tb411)
                .goodsCd(entity.getGoodsCd())
                .standard(entity.getStandard())
                .qty(entity.getQty())
                .price(entity.getPrice())
                .amt(entity.getAmt())
                .vat(entity.getVat())
                .deadDt(entity.getDeadDt())
                .note(entity.getNote())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }
}
