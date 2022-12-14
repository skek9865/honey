package project.honey.personDepart.form;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import project.honey.personDepart.entity.Tb201;

@AllArgsConstructor
@Getter
@Builder
@ToString
public class Tb201Form {
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
    private MultipartFile img;
    private String note;
    private MultipartFile file;
    private String sEmpNm;
    private String sPost;
    private String sDeptCd;
    private String fstId;
    private String scdId;
    private String thdId;

    public static Tb201 toTb201(Tb201Form form, String fileName, String imgName) {
        String headYn = "N";
        if(form.headYn) headYn = "Y";
        return Tb201.builder()
                .seq(form.getSeq())
                .empNo(form.getEmpNo())
                .empNm(form.getEmpNm())
                .emp2Nm(form.getEmp2Nm())
                .empEngNm(form.getEmpEngNm())
                .idNo(form.getIdNo())
                .headYn(headYn)
                .empDt(form.getEmpDt())
                .empClass(form.getEmpClass())
                .post(form.getPost())
                .post1(form.getPost1())
                .leaveDt(form.getLeaveDt())
                .leaveRs(form.getLeaveRs())
                .phone(form.getPhone())
                .mobile(form.getMobile())
                .psNo(form.getPsNo())
                .email(form.getEmail())
                .deptCd(form.getDeptCd())
                .workCd(form.getWorkCd())
                .bankNm(form.getBankNm())
                .aCutNo(form.getACutNo())
                .aCutNm(form.getACutNm())
                .zipCd(form.getZipCd())
                .address(form.getAddress())
                .address1(form.getAddress1())
                .picNm(imgName)
                .note(form.getNote())
                .fileNm(fileName)
                .build();
    }
}
