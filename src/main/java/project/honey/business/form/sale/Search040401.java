package project.honey.business.form.sale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Search040401 {
    private String sYmd1;
    private String sYmd2;
    private String sEmpNo;
    private String sStatus;
    private String sCustCd;
    private String sCustNm;
    private String sGoodsCd;
    private String sGoodsNm;

    public void setYmd(String ymd1, String ymd2){
        this.sYmd1 = ymd1;
        this.sYmd2 = ymd2;
    }
}
