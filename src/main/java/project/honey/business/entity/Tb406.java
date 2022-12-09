package project.honey.business.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.business.form.Tb406Form;
import project.honey.comm.BaseAtt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@Table(name = "tb_406")
public class Tb406 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("특별그룹코드")
    @Column(name = "specialcd", length = 5, columnDefinition = "char")
    private String specialCd;

    @NotNull
    @Comment("특별그룹정렬순서")
    @Column(name = "specialal", length = 11)
    private Integer specialAl;

    @NotNull
    @Comment("특별그룹명")
    @Column(name = "specialnm", length = 50)
    private String specialNm;

    public void updateData(Tb406Form form){
        this.seq = form.getSeq();
        this.specialCd = form.getSpecialCd();
        this.specialAl = form.getSpecialAl();
        this.specialNm = form.getSpecialNm();
    }
}
