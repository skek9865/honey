package project.honey.business.dto.manage;

import lombok.*;
import project.honey.business.entity.manage.Tb414_1;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb414_1Dto {

    private Integer seq;
    private Integer tb414;
    private String goodsCd;
    private String goodsNm;
    private String standard;
    private Integer qty;
    private String note;
    private String qrCode;
    private String qrCode1;
    private String qrCode2;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb414_1Dto(int num){
        this.qty = num;
    }

    public static Tb414_1Dto of(Tb414_1 entity, String goodsNm){
        return Tb414_1Dto.builder()
                .seq(entity.getSeq())
                .tb414(entity.getTb414().getSeq())
                .goodsCd(entity.getGoodsCd())
                .goodsNm(goodsNm)
                .standard(entity.getStandard())
                .qty(entity.getQty())
                .note(entity.getNote())
                .qrCode(entity.getQrCode())
                .qrCode1(entity.getQrCode1())
                .qrCode2(entity.getQrCode2())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }

}
