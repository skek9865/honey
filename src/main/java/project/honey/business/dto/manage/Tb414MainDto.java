package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb414;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb414MainDto {

    private Integer seq;
    private String shipNo;
    private String whouseNm;
    private String custNm;
    private String goods;
    private Integer qty;
    private String prt;

    public static Tb414MainDto of(Tb414 entity, String whouseNm, String custNm, String goods,Integer qty){
        String shipNo = entity.getShipDt().substring(0,4) + "-" + entity.getShipDt().substring(4,6) + "-" + entity.getShipDt().substring(6,8) + "-" + entity.getShipNo();
        String prt = "인쇄전";
        if(entity.getPrtEmp() != null && entity.getPrtDt() != null) prt = "인쇄함";

        return Tb414MainDto.builder()
                .seq(entity.getSeq())
                .shipNo(shipNo)
                .whouseNm(whouseNm)
                .custNm(custNm)
                .goods(goods)
                .qty(qty)
                .prt(prt)
                .build();
    }
}
