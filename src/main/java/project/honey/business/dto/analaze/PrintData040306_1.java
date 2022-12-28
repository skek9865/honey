package project.honey.business.dto.analaze;

import lombok.*;
import project.honey.business.entity.manage.Tb412_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
public class PrintData040306_1 {
    private String goodsNm;
    private String standard;
    private String unit;
    private Integer qty;
    private Integer price;
    private Integer amt;
    private Integer vat;

    public static PrintData040306_1 of(Tb412_1 tb412_1, String goodsNm, String unit){
        return PrintData040306_1.builder()
                .goodsNm(goodsNm)
                .standard(tb412_1.getStandard())
                .unit(unit)
                .qty(tb412_1.getQty())
                .price(tb412_1.getPrice())
                .amt(tb412_1.getAmt())
                .vat(tb412_1.getVat())
                .build();
    }
}
