package project.honey.personDepart.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.personDepart.form.Tb204Form;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_204")
@ToString
public class Tb204 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("회사번호")
    @Column(name = "fk_tb_101", length = 11)
    private Integer compNo;

    @NotNull
    @Comment("구분명")
    @Column(length = 5)
    private String part;

    @NotNull
    @Comment("저장파일명")
    @Column(name = "savefnm", length = 100)
    private String saveFNm;

    @NotNull
    @Comment("출력파일명")
    @Column(name = "outfnm", length = 100)
    private String outFNm;

    @Comment("비고")
    @Column(length = 255)
    private String note;

    public void updateData(Tb204Form form, String saveFNm, String outFNm){
        this.seq = form.getSeq();
        this.compNo = form.getCompNo();
        this.part = form.getPart();
        this.saveFNm = saveFNm;
        this.outFNm = outFNm;
        this.note = form.getNote();
    }
}
