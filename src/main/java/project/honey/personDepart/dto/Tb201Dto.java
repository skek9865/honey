package project.honey.personDepart.dto;

import lombok.*;
import project.honey.personDepart.entity.Tb201;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb201Dto {

    private Integer seq;
    private String empNo;
    private String empNm;
    private String emp2Nm;
    private String empEngNm;
    private String idNo;
    private Boolean headYn;
    private String empDt;
    private String empClass;
    private String post;
    private String post1;
    private String leaveDt;
    private String leaveRs;
    private String phone;
    private String mobile;
    private String psNo;
    private String email;
    private String deptCd;
    private String workCd;
    private String bankNm;
    private String aCutNo;
    private String aCutNm;
    private String zipCd;
    private String address;
    private String address1;
    private String picNm;
    private String note;
    private String fileNm;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public static Tb201Dto of(Tb201 entity, String postNm, String deptNm){
        Boolean headYn = false;
        if(entity.getHeadYn().equals("Y")) headYn = true;

        if(postNm == null) postNm = entity.getPost();
        if(deptNm == null) deptNm = entity.getDeptCd();

        return Tb201Dto.builder()
                .seq(entity.getSeq())
                .empNo(entity.getEmpNo())
                .empNm(entity.getEmpNm())
                .emp2Nm(entity.getEmp2Nm())
                .empEngNm(entity.getEmpEngNm())
                .idNo(entity.getIdNo())
                .headYn(headYn)
                .empDt(entity.getEmpDt())
                .empClass(entity.getEmpClass())
                .post(postNm)
                .post1(entity.getPost1())
                .leaveDt(entity.getLeaveDt())
                .leaveRs(entity.getLeaveRs())
                .phone(entity.getPhone())
                .mobile(entity.getMobile())
                .psNo(entity.getPsNo())
                .email(entity.getEmail())
                .deptCd(deptNm)
                .workCd(entity.getWorkCd())
                .bankNm(entity.getBankNm())
                .aCutNo(entity.getACutNo())
                .aCutNm(entity.getACutNm())
                .zipCd(entity.getZipCd())
                .address(entity.getAddress())
                .address1(entity.getAddress1())
                .picNm(entity.getPicNm())
                .note(entity.getNote())
                .fileNm(entity.getFileNm())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }

}
