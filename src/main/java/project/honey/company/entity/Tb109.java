package project.honey.company.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.company.form.Tb109Form;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@Table(name = "tb_109")
public class Tb109 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("회사코드")
    @Column(name = "fk_tb_101")
    private Integer tb101;

    @NotNull
    @Comment("회사명")
    @Column(length = 50)
    private String company;

    @Comment("연락처")
    @Column(name = "telno", length = 50)
    private String telNo;

    @Comment("핸드폰")
    @Column(length = 50)
    private String mobile;

    @Comment("팩스")
    @Column(length = 50)
    private String fax;

    @Comment("이메일")
    @Column(length = 50)
    private String email;

    @Comment("기타연락처1")
    @Column(name = "etctel1", length = 50)
    private String etcTel1;

    @Comment("기타연락처2")
    @Column(name = "etctel2", length = 50)
    private String etcTel2;

    @Comment("기타연락처3")
    @Column(name = "etctel3", length = 50)
    private String etcTel3;

    @Comment("비고")
    private String note;

    public void updateData(Tb109Form form){
        this.seq = form.getSeq();
        this.tb101 = form.getTb101();
        this.company = form.getCompany();
        this.telNo = form.getTelNo();
        this.mobile = form.getMobile();
        this.fax = form.getFax();
        this.email = form.getEmail();
        this.etcTel1 = form.getEtcTel1();
        this.etcTel2 = form.getEtcTel2();
        this.etcTel3 = form.getEtcTel3();
        this.note = form.getNote();
    }
}
