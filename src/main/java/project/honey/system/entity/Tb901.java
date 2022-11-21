package project.honey.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import project.honey.comm.BaseAtt;
import project.honey.system.dto.Tb901Dto;

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
    @Comment("아이디")
    @Column(name = "userid")
    private String userId;

    @Comment("비밀번호")
    private String passwd;

    @Comment("사용자이름")
    @Column(name = "usernm")
    private String userNm;

    @Comment("전화번호")
    private String phone;

    @Comment("모바일")
    private String mobile;

    @Comment("이메일")
    private String email;

    @Comment("사용자그룹")
    @Column(name = "usergr", columnDefinition = "char")
    private String userGr;

    @Comment("사용여부")
    @Column(name = "useyn", columnDefinition = "char")
    @ColumnDefault("N")
    private String useYn;

    @Comment("사원여부")
    @Column(name = "empyn", columnDefinition = "char")
    @ColumnDefault("N")
    private String empYn;

    @Comment("사원번호")
    @Column(name = "empno")
    private String empNo;

    @Comment("등록일자")
    @Column(name = "regdt", columnDefinition = "char")
    private String regDt;



    // 사용자 정보 변경
    public void changeInfo(Tb901Dto dto) {
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
