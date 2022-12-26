package project.honey.business.form.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb414;
import project.honey.business.entity.manage.Tb414_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Setter
public class Tb414_1Form {
    private Integer tb414;
    private String goodsCd;
    private String goodsNm;
    private String standard;
    private Integer qty;
    private String note;
    private String qrCode;
    private String qrCode1;
    private String qrCode2;

    public static Tb414_1 toTb414_1(Tb414_1Form form, Tb414 tb414){
        return Tb414_1.builder()
                .tb414(tb414)
                .goodsCd(form.getGoodsCd())
                .standard(form.getStandard())
                .qty(form.getQty())
                .note(form.getNote())
                .qrCode(form.getQrCode())
                .qrCode1(form.getQrCode1())
                .qrCode2(form.getQrCode2())
                .build();
    }
}
