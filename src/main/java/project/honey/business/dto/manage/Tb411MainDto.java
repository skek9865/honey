package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb411;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb411MainDto {

    private Integer seq;
    private String orderNo;
    private String custNm;
    private String empNo;
    private String goodsCd;
    private Integer price;
    private Integer qty;
    private String status;
    private String prt;
    private String note;

    public static Tb411MainDto of (Tb411 entity, String custNm, String goodsNm, String empNm, Integer price, Integer qty, String prt, String status){
        String orderDt = entity.getOrderDt();
        String fdt = orderDt.substring(0,4);
        String sdt = orderDt.substring(4,6);
        String tdt = orderDt.substring(6,8);

        String order = fdt + "-" + sdt + "-" + tdt + "-" + entity.getOrderNo();

        return Tb411MainDto.builder()
                .seq(entity.getSeq())
                .orderNo(order)
                .custNm(custNm)
                .empNo(empNm)
                .goodsCd(goodsNm)
                .price(price)
                .qty(qty)
                .status(status)
                .prt(prt)
                .note(entity.getNote())
                .build();
    }
}
