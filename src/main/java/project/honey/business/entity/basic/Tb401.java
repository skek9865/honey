package project.honey.business.entity.basic;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.business.form.basic.Tb401Form;
import project.honey.comm.BaseAtt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_401")
@ToString
public class Tb401 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("거래처구분")
    @Column(name = "classseq", length = 5, columnDefinition = "char")
    private String classSeq;

    @NotNull
    @Comment("거래구분코드")
    @Column(name = "classcd", length = 5, columnDefinition = "char")
    private String classCd;

    @NotNull
    @Comment("거래처정렬순서")
    @Column(name = "classal", length = 11)
    private Integer classAl;

    @NotNull
    @Comment("거래구분명")
    @Column(name = "classnm", length = 50)
    private String classNm;

    public void updateData(Tb401Form form){
        this.seq = form.getSeq();
        this.classSeq = form.getClassSeq();
        this.classCd = form.getClassCd();
        this.classAl = form.getClassAl();
        this.classNm = form.getClassNm();
    }
}
