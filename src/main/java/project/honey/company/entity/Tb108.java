package project.honey.company.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.comm.GlobalMethod;
import project.honey.company.form.Tb108Form;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@Table(name = "tb_108")
public class Tb108 extends BaseAtt {

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
    @Comment("구분")
    @Column(length = 50)
    private String part;

    @Comment("특허번호/등록번호")
    @Column(name = "patentno", length = 50)
    private String patentNo;

    @Comment("출원번호")
    @Column(name = "patentapno", length = 50)
    private String patentApNo;

    @Comment("출원일")
    @Column(name = "patentdt", length = 10)
    private String patentDt;

    @Comment("등록일")
    @Column(name = "regdt", length = 10)
    private String regDt;

    @Comment("명칭/물품류")
    @Column(name = "patentnm", length = 100)
    private String patentNm;

    @Comment("특허권자")
    @Column(name = "patentman", length = 50)
    private String patentMan;

    @Comment("발명자/창작자")
    @Column(length = 50)
    private String inventor;

    @Comment("발급처")
    @Column(name = "issuedby", length = 100)
    private String issueDBy;

    @Comment("비고")
    private String note;

    @Comment("사진")
    @Column(name = "picnm", length = 50)
    private String picNm;

    public void updateData(Tb108Form form, String picNm){
        String patentDt = GlobalMethod.replaceYmd(form.getPatentDt(), "-");
        String regDt = GlobalMethod.replaceYmd(form.getRegDt(), "-");

        this.seq = form.getSeq();
        this.tb101 = form.getTb101();
        this.part = form.getPart();
        this.patentNo = form.getPatentNo();
        this.patentApNo = form.getPatentApNo();
        this.patentDt = patentDt;
        this.regDt = regDt;
        this.patentNm = form.getPatentNm();
        this.patentMan = form.getPatentMan();
        this.inventor = form.getInventor();
        this.issueDBy = form.getIssueDBy();
        this.note = form.getNote();
        this.picNm = picNm;
    }
}
