package project.honey.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
public class Tb903 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;    //순번

    @Column(name = "userid")
    private String userId;  //사용자ID

    @Column(name="tpid")
    private String tpId;    //화면ID

    @Column(name = "menuyn", columnDefinition = "char")
    private String menuYn;  //접근여부

    @Column(name = "listyn", columnDefinition = "char")
    private String listYn;  //목록사용여부

    @Column(name = "viewyn", columnDefinition = "char")
    private String viewYn;  //건별조회사용여부

    @Column(name = "saveyn", columnDefinition = "char")
    private String saveYn;  //저장사용여부

    @Column(name = "modifyyn", columnDefinition = "char")
    private String modifyYn;    //수정사용여부

    @Column(name = "delyn", columnDefinition = "char")
    private String delYn;   //삭제사용여부

    @Column(name = "inputid")
    private String inputId; //입력아이디

    @Column(name = "inputdt", columnDefinition = "char")
    private String inputDt; //입력일시

    @Column(name = "updateid")
    private String updateId;    //수정아이디

    @Column(name = "updatedt", columnDefinition = "char")
    private String updateDt;    //수정일시
}
