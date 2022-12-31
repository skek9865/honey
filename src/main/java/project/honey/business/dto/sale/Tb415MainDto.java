package project.honey.business.dto.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb415;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb415MainDto {
    private Integer seq;
    private String orderNo;
    private String custNm;
    private String empNm;
    private String goods;
    private String deadDt;
    private Integer price;
    private String status;
    private String prt;

    public static Tb415MainDto of(Tb415 entity, String custNm, String empNm, String goods, Integer price, String status){
        String orderDt = entity.getOrderDt().substring(0,4) + "-" + entity.getOrderDt().substring(4,6) + "-" + entity.getOrderDt().substring(6,8) + "-" + entity.getOrderNo();
        String deadDt = entity.getDeadDt().substring(0,4) + "-" + entity.getDeadDt().substring(4,6) + "-" + entity.getDeadDt().substring(6,8);
        String prt = "인쇄전";
        if(entity.getPrtEmp() != null && entity.getPrtDt() != null) prt = "인쇄함";

        return Tb415MainDto.builder()
                .seq(entity.getSeq())
                .orderNo(orderDt)
                .custNm(custNm)
                .empNm(empNm)
                .goods(goods)
                .deadDt(deadDt)
                .price(price)
                .status(status)
                .prt(prt)
                .build();
    }
}
