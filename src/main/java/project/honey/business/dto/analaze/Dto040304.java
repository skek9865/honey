package project.honey.business.dto.analaze;

import lombok.*;
import project.honey.business.entity.manage.Tb413;
import project.honey.business.entity.manage.Tb413_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Dto040304 {
    private Integer seq;
    private String shipNo;
    private String goodsNm;
    private String standard;
    private Integer qty;
    private String whouseNm;
    private String custNm;
    private String phone;
    private String note;

    public static Dto040304 of(Tb413 entity, Tb413_1 tb413_1, String goodsNm, String whouseNm, String custNm){
        String shipNo = entity.getShipDt().substring(0,4) + "-" + entity.getShipDt().substring(4,6) + "-" + entity.getShipDt().substring(6,8) + "-" + entity.getShipNo();

        return Dto040304.builder()
                .seq(entity.getSeq())
                .shipNo(shipNo)
                .goodsNm(goodsNm)
                .standard(tb413_1.getStandard())
                .qty(tb413_1.getQty())
                .whouseNm(whouseNm)
                .custNm(custNm)
                .phone(entity.getPhone())
                .note(tb413_1.getNote())
                .build();
    }
}
