package project.honey.business.form.sale;

import lombok.*;
import project.honey.business.entity.sale.Tb415;
import project.honey.business.entity.sale.Tb415_1;
import project.honey.comm.GlobalMethod;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Setter
public class Tb415_1Form {
    private Integer seq;
    private Integer tb415;
    private String goodsCd;
    private String goodsNm;
    private String standard;
    private Integer qty;
    private Integer price;
    private Integer amt;
    private Integer vat;
    private String deadDt;
    private String note;

    public static Tb415_1 toTb415_1(Tb415_1Form form, Tb415 tb415){
        String deadDt = GlobalMethod.replaceYmd(form.getDeadDt(), "-");

        return Tb415_1.builder()
                .tb415(tb415)
                .goodsCd(form.getGoodsCd())
                .standard(form.getStandard())
                .qty(form.getQty())
                .price(form.getPrice())
                .amt(form.getAmt())
                .vat(form.getVat())
                .deadDt(deadDt)
                .note(form.getNote())
                .build();
    }
}
