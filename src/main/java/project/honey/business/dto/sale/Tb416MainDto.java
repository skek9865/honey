package project.honey.business.dto.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb416;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb416MainDto {

    private Integer seq;
    private String buyNo;
    private String custNm;
    private String goods;
    private Integer price;
    private Integer qty;
    private String saleType;
    private String whouseNm;
    private String status;
    private String prt;

    public static Tb416MainDto of(Tb416 entity, String custNm, String goods, Integer price,
                               Integer qty, String saleType, String whouseNm, String status){
        String buyNo = entity.getBuyDt().substring(0,4) + "-" + entity.getBuyDt().substring(4,6) + "-" + entity.getBuyDt().substring(6,8) + "-" + entity.getBuyNo();
        String prt = "인쇄전";
        if(entity.getPrtEmp() != null && entity.getPrtDt() != null) prt = "인쇄함";

        return Tb416MainDto.builder()
                .seq(entity.getSeq())
                .buyNo(buyNo)
                .custNm(custNm)
                .goods(goods)
                .price(price)
                .qty(qty)
                .saleType(saleType)
                .whouseNm(whouseNm)
                .status(status)
                .prt(prt)
                .build();
    }

}
