package project.honey.pay.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_302")
@ToString
public class Tb302 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @NotNull
    @Comment("사원번호")
    @Column(name = "empno", length = 10)
    private String empNo;

    @Comment("공제/지급구분")
    @Column(name = "itemdiv", columnDefinition = "char")
    private String itemDiv;

    @Comment("과세여부")
    @Column(name = "taxdiv", columnDefinition = "char")
    @ColumnDefault("N")
    private String taxDiv;

    @Comment("항목코드")
    @Column(name = "itemcd", columnDefinition = "char")
    private String itemCd;

    @Comment("금액")
    @Column(name = "payamt", columnDefinition = "float")
    private Double payAmt;
}
