package project.honey.business.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.business.form.Tb408Form;
import project.honey.comm.BaseAtt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@Table(name = "tb_408")
public class Tb408 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("프로젝트코드")
    @Column(name = "projectcd", length = 5, columnDefinition = "char")
    private String projectCd;

    @NotNull
    @Comment("프로젝트정렬순서")
    @Column(name = "projectal", length = 11)
    private Integer projectAl;

    @NotNull
    @Comment("프로젝트명")
    @Column(name = "projectnm", length = 50)
    private String projectNm;

    public void updateData(Tb408Form form){
        this.seq = form.getSeq();
        this.projectCd = form.getProjectCd();
        this.projectAl = form.getProjectAl();
        this.projectNm = form.getProjectNm();
    }
}
