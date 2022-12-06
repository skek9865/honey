package project.honey.business.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.business.form.Tb401Form;
import project.honey.business.form.Tb404Form;
import project.honey.comm.BaseAtt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_404")
@ToString
public class Tb404 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("품목분류")
    @Column(name = "classseq", length = 5, columnDefinition = "char")
    private String classSeq;

    @NotNull
    @Comment("품목구분코드")
    @Column(name = "classcd", length = 5, columnDefinition = "char")
    private String classCd;

    @NotNull
    @Comment("품목구분정렬순서")
    @Column(name = "classal", length = 11)
    private Integer classAl;

    @NotNull
    @Comment("품목구분명")
    @Column(name = "classnm", length = 50)
    private String classNm;

    public void updateData(Tb404Form form){
        this.seq = form.getSeq();
        this.classSeq = form.getClassSeq();
        this.classCd = form.getClassCd();
        this.classAl = form.getClassAl();
        this.classNm = form.getClassNm();
    }
}
