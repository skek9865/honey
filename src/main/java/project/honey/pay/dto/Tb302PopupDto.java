package project.honey.pay.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import project.honey.pay.entity.Tb301;
import project.honey.pay.entity.Tb302;
import project.honey.personDepart.entity.Tb201;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tb302PopupDto {

    private Integer seq;
    private String empNo;

    private String empNm;
    private String post;

    private String itemDiv;
    private String taxDiv;
    private String itemCd;
    private Double payAmt;

    @QueryProjection
    public Tb302PopupDto(Integer seq, String empNo, String empNm, String post, String itemDiv, String taxDiv, String itemCd, Double payAmt) {
        this.seq = seq;
        this.empNo = empNo;
        this.empNm = empNm;
        this.post = post;
        this.itemDiv = itemDiv;
        this.taxDiv = taxDiv;
        this.itemCd = itemCd;
        this.payAmt = payAmt;
    }
}
