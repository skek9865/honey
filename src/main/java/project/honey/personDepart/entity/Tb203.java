package project.honey.personDepart.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.personDepart.form.Tb203Form;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_203")
@ToString
public class Tb203 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("사원번호")
    @Column(name = "empno", length = 30)
    private String empNo;

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

    public void updateData(Tb203Form form, String saveFNm, String outFNm){
        this.seq = form.getSeq();
        this.empNo = form.getEmpNo();
        this.part = form.getPart();
        this.saveFNm = saveFNm;
        this.outFNm = outFNm;
        this.note = form.getNote();
    }
}
