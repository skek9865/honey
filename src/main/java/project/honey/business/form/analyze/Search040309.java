package project.honey.business.form.analyze;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Search040309 {

    private String sYmd1;
    private String sYmd2;
    private String sVatYn;

    public void setYmd(String ymd1, String ymd2){
        this.sYmd1 = ymd1;
        this.sYmd2 = ymd2;
    }
}
