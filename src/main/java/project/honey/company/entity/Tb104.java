package project.honey.company.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.company.dto.Tb104Dto;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Table(name = "tb_104")
public class Tb104 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    @Comment("순번")
    private Integer seq;

    @Column(name = "fk_tb_101")
    @Comment("회사코드")
    private Integer fk_tb_101;

    @Column(name = "cardnm")
    @Comment("카드명")
    private String cardnm;

    @Column(name = "cardno")
    @Comment("카드번호")
    private String cardno;

    @Column(name = "expdt")
    @Comment("유효기간")
    private String expdt;

    @Column(name = "cvcno")
    @Comment("cvc번호")
    private String cvcno;

    @Column(name = "useyn", columnDefinition = "char")
    @Comment("사용여부")
    private String useyn;

    @Column(name = "empno")
    @Comment("사용자")
    private String empno;

    @Column(name = "fk_tb_102")
    @Comment("결제계좌")
    private Integer fk_tb_102;

    @Column(name = "limitamt")
    @Comment("한도")
    private Integer limitamt;

    @Column(name = "issuedt")
    @Comment("발급일자")
    private String issuedt;

    @Column(name = "fk_tb_103")
    @Comment("인증서")
    private Integer fk_tb_103;

    @Column(name = "note")
    @Comment("비고")
    private String note;

    public void changInfo(Tb104Dto dto){
        this.fk_tb_101 = dto.getFk_tb_101();
        this.cardnm = dto.getCardnm();
        this.cardno = dto.getCardno();
        this.expdt = dto.getExpdt();
        this.cvcno = dto.getCvcno();
        this.useyn = dto.getUseyn();
        this.empno = dto.getEmpno();
        this.fk_tb_102 = Integer.parseInt(dto.getFk_tb_102());
        this.limitamt = dto.getLimitamt();
        this.issuedt = dto.getIssuedt();
        this.fk_tb_103 = Integer.parseInt(dto.getFk_tb_103());
        this.note = dto.getNote();
    }
}
