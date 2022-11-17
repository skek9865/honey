package project.honey.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.honey.comm.BaseAtt;
import project.honey.user.UserDto;

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
public class Tb901 extends BaseAtt {

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

    @Column(name = "useyn", columnDefinition = "char")
    private String useYn;  //사용여부

    @Column(name = "empyn", columnDefinition = "char")
    private String empYn;   //사원여부

    @Column(name = "empno")
    private String empNo;   //사원번호

    @Column(name = "regdt", columnDefinition = "char")
    private String regDt;   //등록일자



    // 사용자 정보 변경
    public void changeInfo(UserDto dto) {
        this.passwd = dto.getPasswd();
        this.userNm = dto.getUserNm();
        this.phone = dto.getPhone();
        this.mobile = dto.getMobile();
        this.email = dto.getEmail();
        this.userGr = dto.getUserGr();
        this.useYn = dto.getUseYn();
        this.empYn = dto.getEmpYn();
        this.empNo = dto.getEmpNo();
        this.regDt = dto.getRegDt();
    }
}
