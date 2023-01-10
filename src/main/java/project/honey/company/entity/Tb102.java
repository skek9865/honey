package project.honey.company.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.company.dto.Tb102Dto;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "tb_102")
public class Tb102 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Column(name = "fk_tb_101")
    @Comment("회사코드")
    private Integer fk_tb_101;

    @Column(name = "accountno")
    @Comment("계좌번호")
    private String accountno;

    @Column(name = "banknm")
    @Comment("은행명")
    private String banknm;

    @Column(name = "accounhd")
    @Comment("예금주")
    private String accounhd;

    @Column(name = "usenote")
    @Comment("용도")
    private String usenote;

    @Column(name = "accountid")
    @Comment("이용자ID")
    private String accountid;

    @Column(name = "stdate")
    @Comment("개설일")
    private String stdate;

    @Column(name = "note")
    @Comment("적요")
    private String note;

    @Column(name = "note1")
    @Comment("참조")
    private String note1;

    @Column(name = "useyn")
    @Comment("사용여부")
    private String useyn;

    public void changeInfo(Tb102Dto dto){
        this.accountno = dto.getAccountno();
        this.banknm = dto.getBanknm();
        this.accounhd = dto.getAccounhd();
        this.usenote = dto.getUsenote();
        this.accountid = dto.getAccountid();
        this.stdate = dto.getStdate();
        this.note = dto.getNote();
        this.note1 = dto.getNote1();
        this.useyn = dto.getUseyn();
    }

}
