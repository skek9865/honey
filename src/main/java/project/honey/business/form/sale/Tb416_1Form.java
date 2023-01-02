package project.honey.business.form.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb416;
import project.honey.business.entity.sale.Tb416_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Setter
public class Tb416_1Form {
    private Integer seq;
    private Tb416 tb416;
    private String goodsCd;
    private String goodsNm;
    private String standard;
    private Integer qty;
    private Integer price;
    private Integer amt;
    private Integer vat;
    private String note;

    public static Tb416_1 toTb416_1(Tb416_1Form form, Tb416 tb416){
        return Tb416_1.builder()
                .tb416(tb416)
                .goodsCd(form.getGoodsCd())
                .standard(form.getStandard())
                .qty(form.getQty())
                .price(form.getPrice())
                .amt(form.getAmt())
                .vat(form.getVat())
                .note(form.getNote())
                .build();
    }
}
