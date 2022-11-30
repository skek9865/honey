package project.honey.company.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.company.dto.Tb103Dto;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Table(name ="tb_103")
public class Tb103 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    @Comment("순번")
    private Integer seq;

    @Column(name = "fk_tb_101")
    @Comment("회사코드")
    private Integer fk_tb_101;

    @Column(name = "cernm")
    @Comment("인증서명")
    private String cernm;

    @Column(name = "expdt")
    @Comment("만료일")
    private String expdt;

    @Column(name = "usenote")
    @Comment("사용용도")
    private String usenote;

    @Column(name = "savemtd")
    @Comment("보관형태")
    private String savemtd;

    @Column(name = "empno")
    @Comment("관리자")
    private String empno;

    @Column(name = "useyn", columnDefinition = "char")
    @Comment("사용여부")
    private String useyn;

    @Column(name = "note")
    @Comment("비고")
    private String note;


    public void changInfo(Tb103Dto dto) {
        this.cernm = dto.getCernm();
        this.expdt = dto.getExpdt();
        this.usenote = dto.getUsenote();
        this.savemtd = dto.getSavemtd();
        this.empno = dto.getEmpno();
        this.useyn = dto.getUseyn();
        this.note = dto.getNote();
    }
}
