package project.honey.pay.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tb303PopupDto {

    private Integer seq;
    private String empNo;

    private String empNm;
    private String post;
    private String payDt;
    private String rPayDt;

    private Double taxRate;
    private String itemDiv;
    private String taxDiv;
    private String itemCd;
    private Double payAmt;

    @QueryProjection
    public Tb303PopupDto(Integer seq, String empNo, String empNm, String post, String itemDiv,
                         String taxDiv, String itemCd, Double payAmt, Double taxRate,
                         String payDt, String rPayDt) {
        this.seq = seq;
        this.empNo = empNo;
        this.empNm = empNm;
        this.post = post;
        this.itemDiv = itemDiv;
        this.taxDiv = taxDiv;
        this.itemCd = itemCd;
        this.payAmt = payAmt;
        this.taxRate = taxRate;
        this.payDt = payDt;
        this.rPayDt = rPayDt;
    }
}
