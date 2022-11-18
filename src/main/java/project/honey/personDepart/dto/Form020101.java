package project.honey.personDepart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Getter
public class Form020101 {
    private Integer seq;
    private String empNo;
    private String empNm;
    private String emp2Nm;
    private String empEngNm;
    private String idNo;
    private String headYn;
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
}
