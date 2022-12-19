package project.honey.business.form.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb410;
import project.honey.business.entity.manage.Tb410_1;

import java.math.BigDecimal;

@NoArgsConstructor
@Builder
@Getter
@ToString
@Setter
public class Tb410_1Form {

    private Integer seqC;
    private Integer tb410;
    private String goodsCd;
    private String standard;
    private Integer qty;
    private Integer price;
    private Integer amt;
    private Integer vat;
    private String noteC;

    public static Tb410_1 toTb410_1(Tb410_1Form form, Tb410 tb410){
        return Tb410_1.builder()
                .tb410(tb410)
                .goodsCd(form.getGoodsCd())
                .standard(form.getStandard())
                .qty(form.getQty())
                .price(form.getPrice())
                .amt(form.getAmt())
                .vat(form.getVat())
                .note(form.getNoteC())
                .build();
    }

    public Tb410_1Form(Integer seqC, Integer tb410, String goodsCd, String standard, Integer qty, Integer price, Integer amt, Integer vat, String noteC) {
        this.seqC = seqC;
        this.tb410 = tb410;
        this.goodsCd = goodsCd;
        this.standard = standard;
        this.qty = qty;
        this.price = price;
        this.amt = amt;
        this.vat = vat;
        this.noteC = noteC;
    }
}
