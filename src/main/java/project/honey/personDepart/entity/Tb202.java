package project.honey.personDepart.entity;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_202")
@ToString
public class Tb202 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    @Column(length = 11)
    private Integer seq;

    @NotNull
    @Comment("부서코드")
    @Column(name = "deptcd", length = 5, columnDefinition = "char")
    private String deptCd;

    @NotNull
    @Comment("부서명")
    @Column(name = "deptnm", length = 30)
    private String deptNm;

    @NotNull
    @Comment("사용여부")
    @Column(name = "useyn", length = 2, columnDefinition = "char")
    private String useYn;
}
