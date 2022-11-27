package project.honey.pay.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.pay.dto.Tb302Dto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_303")
@ToString
public class Tb303 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Comment("지급년월")
    @Column(name = "paydt", columnDefinition = "char")
    private String payDt;

    @NotNull
    @Comment("사원번호")
    @Column(name = "empno", length = 10)
    private String empNo;

    @Comment("공제/지급구분")
    @Column(name = "itemdiv", columnDefinition = "char")
    private String itemDiv;

    @Comment("세제율")
    @Column(name = "taxrate", columnDefinition = "float")
    private Double taxRate;

    @Comment("항목코드")
    @Column(name = "itemcd", columnDefinition = "char")
    private String itemCd;

    @Comment("금액")
    @Column(name = "payamt", columnDefinition = "float")
    private Double payAmt;

    @Comment("실지급일")
    @Column(name = "rpaydt", columnDefinition = "char")
    private String rPayDt;

    public void changeInfo(Tb302Dto dto) {
        this.itemCd = dto.getItemCd();
        this.payAmt = dto.getPayAmt().doubleValue();
    }
}
