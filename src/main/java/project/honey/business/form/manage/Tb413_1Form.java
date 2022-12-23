package project.honey.business.form.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb413;
import project.honey.business.entity.manage.Tb413_1;
import project.honey.comm.GlobalMethod;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Setter
public class Tb413_1Form {
    private Integer seq;
    private Integer tb413;
    private String goodsCd;
    private String goodsNm;
    private String standard;
    private Integer qty;
    private String note;
    private String productDt;

    public static Tb413_1 toTb413_1(Tb413_1Form tb413_1Form, Tb413 tb413){
        String productDt = GlobalMethod.replaceYmd(tb413_1Form.getProductDt(), "-");

        return Tb413_1.builder()
                .tb413(tb413)
                .goodsCd(tb413_1Form.getGoodsCd())
                .standard(tb413_1Form.getStandard())
                .qty(tb413_1Form.getQty())
                .note(tb413_1Form.getNote())
                .productDt(productDt)
                .build();
    }
}
