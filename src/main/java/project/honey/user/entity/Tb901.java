package project.honey.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/*
 * 사용자관리
 *  User
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_901")
public class Tb901 {

    @Id
    @Column(name = "userid")
    private String userId;  //아이디

    private String passwd;  //비밀번호

    @Column(name = "usernm")
    private String userNm;  //사용자이름

    private String phone;   //전화번호

    private String mobile;  //모바일

    private String email;   //이메일

    @Column(name = "usergr", columnDefinition = "char")
    private String userGr;  //사용자그룹

    @Column(name = "useryn", columnDefinition = "char")
    private String userYn;  //사용여부

    @Column(name = "empyn", columnDefinition = "char")
    private String empYn;   //사원여부

    @Column(name = "empno")
    private String empNo;   //사원번호

    @Column(name = "regdt", columnDefinition = "char")
    private String regDt;   //등록일자

    @Column(name = "inputid")
    private String inputId; //입력아이디

    @Column(name = "inputdt", columnDefinition = "char")
    private String inputDt; //입력일시

    @Column(name = "updateid")
    private String updateId;    //수정아이디

    @Column(name = "updatedt", columnDefinition = "char")
    private String updateDt;    //수정일시
}
