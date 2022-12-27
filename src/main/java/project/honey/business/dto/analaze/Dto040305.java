package project.honey.business.dto.analaze;

import lombok.*;
import project.honey.business.entity.manage.Tb414;
import project.honey.business.entity.manage.Tb414_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Dto040305 {
    private Integer seq;
    private String shipNo;
    private String goodsNm;
    private String standard;
    private Integer qty;
    private String whouseNm;
    private String custNm;
    private String phone;
    private String note;

    public static Dto040305 of(Tb414 entity, Tb414_1 tb414_1, String goodsNm, String whouseNm, String custNm){
        String shipNo = entity.getShipDt().substring(0,4) + "-" + entity.getShipDt().substring(4,6) + "-" + entity.getShipDt().substring(6,8) + "-" + entity.getShipNo();

        return Dto040305.builder()
                .seq(entity.getSeq())
                .shipNo(shipNo)
                .goodsNm(goodsNm)
                .standard(tb414_1.getStandard())
                .qty(tb414_1.getQty())
                .whouseNm(whouseNm)
                .custNm(custNm)
                .phone(entity.getPhone())
                .note(tb414_1.getNote())
                .build();
    }
}
