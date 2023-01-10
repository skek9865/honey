package project.honey.business.dto.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb415;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class PopUp040401Dto {
    private Integer seq;
    private String orderNo;
    private String custNm;
    private String empNm;
    private String goods;
    private Integer qty;

    public static PopUp040401Dto of(Tb415 entity, String custNm, String empNm,
                                    String goods, Integer qty){
        String orderNo = entity.getOrderDt().substring(0,4) + "-" + entity.getOrderDt().substring(4,6) + "-" + entity.getOrderDt().substring(6,8) + "-" + entity.getOrderNo();

        return PopUp040401Dto.builder()
                .seq(entity.getSeq())
                .orderNo(orderNo)
                .custNm(custNm)
                .empNm(empNm)
                .goods(goods)
                .qty(qty)
                .build();
    }
}
