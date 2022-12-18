package project.honey.stock.dto.form;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.stock.dto.Tb603_1Dto;
import project.honey.stock.entity.Tb603;
import project.honey.stock.entity.Tb604;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb604Form {

    private Integer seq;
    private String wHouseDt;
    private Integer wHouseNo;
    private String empNo;
    private String wHouseOut;
    private String goodsCd;
    private String goodsNm;
    private Integer stQty;
    private Integer reQty;
    private Integer adQty;
    private String note;

    public static Tb604Form of(Tb604 tb604, Map<String, String> goodsMap) {
        return Tb604Form.builder()
                .seq(tb604.getSeq())
                .wHouseDt(GlobalMethod.makeYmd(tb604.getWHouseDt(), "yyyy-MM-dd"))
                .wHouseNo(tb604.getWHouseNo())
                .empNo(tb604.getEmpNo())
                .wHouseOut(tb604.getWHouseOut())
                .goodsCd(tb604.getGoodsCd())
                .goodsNm(goodsMap.get(tb604.getGoodsCd()))
                .stQty(tb604.getStQty())
                .reQty(tb604.getReQty())
                .adQty(tb604.getAdQty())
                .note(tb604.getNote())
                .build();
    }

    public static Tb604 toTb604(Tb604Form form) {
        return Tb604.builder()
                .wHouseDt(GlobalMethod.replaceYmd(form.getWHouseDt(), "-"))
                .wHouseNo(form.getWHouseNo())
                .empNo(form.getEmpNo())
                .wHouseOut(form.getWHouseOut())
                .goodsCd(form.getGoodsCd())
                .stQty(form.getStQty())
                .reQty(form.getReQty())
                .adQty(form.getAdQty())
                .note(form.getNote())
                .build();
    }
}
