package project.honey.business.form.manage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Search040203 {
    private String sYmd1;
    private String sYmd2;
    private String sName;
    private String sAddress;
    private String sCustCd;
    private String sCustNm;
    private String sGoodsCd;
    private String sGoodsNm;
    private String sNote;
    private String sNote2;
    private String sEmpNo;
    private String sProjectCd;
    private String sStatus;
    private String sWhouseCd;

    public void setYmd(String ymd1, String ymd2){
        this.sYmd1 = ymd1;
        this.sYmd2 = ymd2;
    }
}
