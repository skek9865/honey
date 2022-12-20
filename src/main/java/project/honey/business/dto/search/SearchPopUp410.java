package project.honey.business.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchPopUp410 {
    private String sYmd1;
    private String sYmd2;
    private String sGoodsCd;
    private String sGoodsNm;
    private String sCustCd;
    private String sCustNm;

    public void setYmd(String ymd1, String ymd2){
        this.sYmd1 = ymd1;
        this.sYmd2 = ymd2;
    }
}
