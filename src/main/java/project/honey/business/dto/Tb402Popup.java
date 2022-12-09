package project.honey.business.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb402Popup {

    private Integer seq;
    private String custCd;
    private String custNm;
    private String ceoNm;
    private String empNm;

    @QueryProjection
    public Tb402Popup(Integer seq, String custCd, String custNm, String ceoNm, String empNm) {
        this.seq = seq;
        this.custCd = custCd;
        this.custNm = custNm;
        this.ceoNm = ceoNm;
        this.empNm = empNm;
    }
}
