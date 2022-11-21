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
    private String modifyDate;
    private String updateId;

    public static Tb201 toTb201(Tb201Dto dto) {
        String headYn;
        if(dto.headYn) headYn = "Y";
        else headYn = "N";
        return Tb201.builder()
                .empNo(dto.getEmpNo())
                .empNm(dto.getEmpNm())
                .emp2Nm(dto.getEmp2Nm())
                .empEngNm(dto.getEmpEngNm())
                .idNo(dto.getIdNo())
                .headYn(headYn)
                .empDt(dto.getEmpDt())
                .empClass(dto.getEmpClass())
                .post(dto.getPost())
                .post1(dto.getPost1())
                .leaveDt(dto.getLeaveDt())
                .leaveRs(dto.getLeaveRs())
                .phone(dto.getPhone())
                .mobile(dto.getMobile())
                .psNo(dto.getPsNo())
                .email(dto.getEmail())
                .deptCd(dto.getDeptCd())
                .workCd(dto.getWorkCd())
                .bankNm(dto.getBankNm())
                .aCutNo(dto.getACutNo())
                .aCutNm(dto.getACutNm())
                .zipCd(dto.getZipCd())
                .address(dto.getAddress())
                .address1(dto.getAddress1())
                .picNm(dto.getPicNm())
                .note(dto.getNote())
                .fileNm(dto.getFileNm())
                .build();
    }

    public static Tb201Dto of(Tb201 entity){
        Boolean headYn;
        if(entity.getHeadYn() == "Y") headYn = true;
        else headYn = false;
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
                .post(entity.getPost())
                .post1(entity.getPost1())
                .leaveDt(entity.getLeaveDt())
                .leaveRs(entity.getLeaveRs())
                .phone(entity.getPhone())
                .mobile(entity.getMobile())
                .psNo(entity.getPsNo())
                .email(entity.getEmail())
                .deptCd(entity.getDeptCd())
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
                .modifyDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }

}
