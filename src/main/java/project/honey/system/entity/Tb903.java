package project.honey.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;

import javax.persistence.*;

/*
 * 사용자 권한 관리
 * UserRole
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_903")
public class Tb903  extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("순번")
    private Integer seq;

    @Comment("사용자ID")
    @Column(name = "userid")
    private String userId;

    @Comment("화면ID")
    @Column(name="tpid")
    private String tpId;

    @Comment("접근여부")
    @Column(name = "menuyn", columnDefinition = "char")
    private String menuYn;

    @Comment("목록사용여부")
    @Column(name = "listyn", columnDefinition = "char")
    private String listYn;

    @Comment("건별조회사용여부")
    @Column(name = "viewyn", columnDefinition = "char")
    private String viewYn;

    @Comment("저장사용여부")
    @Column(name = "saveyn", columnDefinition = "char")
    private String saveYn;

    @Comment("수정사용여부")
    @Column(name = "modifyyn", columnDefinition = "char")
    private String modifyYn;

    @Comment("삭제사용여부")
    @Column(name = "delyn", columnDefinition = "char")
    private String delYn;

}
