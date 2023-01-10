package project.honey.business.form.sale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Search040402 {
    private String sYmd1;
    private String sYmd2;
    private String sWhouseCd;
    private String sSaleType;
    private String sProjectCd;
    private String sCustGr;
    private String sCustCd;
    private String sCustNm;
    private String sGoodsCd;
    private String sGoodsNm;
    private String sEmpNo;
    private String sStatus;

    public void setYmd(String ymd1, String ymd2){
        this.sYmd1 = ymd1;
        this.sYmd2 = ymd2;
    }
}
