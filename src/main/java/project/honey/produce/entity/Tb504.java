package project.honey.produce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_504")
public class Tb504 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @NotNull
    @Comment("작업일")
    @Column(name = "workdt", length = 10, columnDefinition = "char")
    private String workDt;

    @Comment("작업지시순번")
    @Column(name = "workdtno", length = 11)
    private Integer workDtNo;

    @NotNull
    @Comment("거래처코드")
    @Column(name = "custcd", length = 10, columnDefinition = "char")
    private String custCd;

    @Comment("생산공정코드")
    @Column(name = "productcd", length = 5, columnDefinition = "char")
    private String productCd;

    @Comment("담당자")
    @Column(name = "empno", length = 10)
    private String empNo;

    @Comment("납기일자")
    @Column(name = "deaddt", length = 10, columnDefinition = "char")
    private String deadDt;

    @Comment("특이사항")
    @Column(columnDefinition = "TEXT")
    private String note;

    @NotNull
    @Comment("상태")
    @Column(length = 5, columnDefinition = "char")
    private String status;

    @NotNull
    @Comment("종결여부")
    @Column(name="closeyn",length = 2, columnDefinition = "char")
    private String closeYn;

    @Comment("인쇄자")
    @Column(name="prtemp",length = 30)
    private String prtEmp;

    @Comment("인쇄일시")
    @Column(name="prtdt",length = 20, columnDefinition = "char")
    private String prtDt;

    @OneToMany(mappedBy = "tb504", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Tb504_1> tb504_1s;

}
