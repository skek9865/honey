package project.honey.system.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.web.multipart.MultipartFile;
import project.honey.system.entity.Tb901;

@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Tb901Dto {

    private String userId;  //아이디
    private String passwd;  //비밀번호
    private String userNm;  //사용자이름
    private String phone;   //전화번호
    private String mobile;  //모바일
    private String email;   //이메일
    private String userGr;  //사용자그룹
    private String useYn;  //사용여부
    private String empYn;   //사원여부
    private String empNo;   //사원번호
    private String regDt;   //등록일자

    @QueryProjection
    public Tb901Dto(String userId, String passwd, String userNm, String phone, String mobile, String email, String userGr, String useYn, String empYn, String empNo, String regDt) {
        this.userId = userId;
        this.passwd = passwd;
        this.userNm = userNm;
        this.phone = phone;
        this.mobile = mobile;
        this.email = email;
        this.userGr = userGr;
        this.useYn = useYn;
        this.empYn = empYn;
        this.empNo = empNo;
        this.regDt = regDt;
    }

    //임시 기본값 설정
    public void setUseYn(String useYn) {
        this.useYn = useYn!=null?useYn:"N";
    }
    public void setEmpYn(String empYn) {
        this.empYn = empYn!=null?empYn:"N";
    }

    // Entity 변환 메서드
    public static Tb901 toTb901(Tb901Dto dto) {

        return Tb901.builder()
                .userId(dto.getUserId())
                .passwd(dto.getPasswd())
                .userNm(dto.getUserNm())
                .phone(dto.getPhone())
                .mobile(dto.getMobile())
                .email(dto.getEmail())
                .userGr(dto.getUserGr())
                .useYn(dto.getUseYn())
                .empYn(dto.getEmpYn())
                .empNo(dto.getEmpNo())
                .regDt(dto.getRegDt())
                .build();
    }

    //Dto 변환 메서드
    public static Tb901Dto of(Tb901 entity) {

        return Tb901Dto.builder()
                .userId(entity.getUserId())
                .passwd(entity.getPasswd())
                .userNm(entity.getUserNm())
                .phone(entity.getPhone())
                .mobile(entity.getMobile())
                .email(entity.getEmail())
                .userGr(entity.getUserGr())
                .useYn(entity.getUseYn())
                .empYn(entity.getEmpYn())
                .empNo(entity.getEmpNo())
                .regDt(entity.getRegDt())
                .build();
    }
}
