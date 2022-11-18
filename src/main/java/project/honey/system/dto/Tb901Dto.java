package project.honey.system.dto;

import lombok.*;
import project.honey.system.entity.Tb901;

@AllArgsConstructor
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
    private String inputId; //입력아이디
    private String updateId;    //수정아이디

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
                .inputId(entity.getCreateId())
                .updateId(entity.getUpdateId())
                .build();
    }
}
