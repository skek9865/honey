package project.honey.company.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.company.dto.Tb105Dto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Table(name = "tb_105")
public class Tb105 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    @Comment("순번")
    private Integer seq;

    @Column(name = "fk_tb_101")
    @Comment("회사코드")
    private Integer fk_tb_101;

    @Column(name = "loannm")
    @Comment("대출명")
    private String loannm;

    @Column(name = "fk_tb_102")
    @Comment("계좌번호")
    private Integer fk_tb_102;

    @Column(name = "newdt")
    @Comment("신규일")
    private String newdt;

    @Column(name = "expdt")
    @Comment("만기일")
    private String expdt;

    @Column(name = "limitamt")
    @Comment("약정한도")
    private Integer limitamt;

    @Column(name = "ipaydt")
    @Comment("이자일")
    private String ipaydt;

    @Column(name = "irate")
    @Comment("금리")
    private float irate;

    @Column(name = "note")
    @Comment("비고")
    private String note;

    @OneToMany(mappedBy = "fk_tb_105", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tb105_1> tb105_1List = new ArrayList<>();

    public void changeInfo(Tb105Dto dto){
        this.fk_tb_101 = dto.getFk_tb_101();
        this.loannm = dto.getLoannm();
        this.fk_tb_102 = dto.getFk_tb_102();
        this.newdt = dto.getNewdt();
        this.expdt = dto.getExpdt();
        this.limitamt = dto.getLimitamt();
        this.ipaydt = dto.getIpaydt();
        this.irate = dto.getIrate();
        this.note = dto.getNote();
    }
}
