package project.honey.company.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@ToString
public class CompanyForm {

    private String corpnm;

    private String corpno;

    private String ceonm;

    private String setdt;

    private String corptel;

    private String hometel;

    private String email;

    private String mobile;

    private String corpfax;

    private String hompage;

    private String zipcd1;

    private String address1;

    private String address11;

    private String zipcd2;

    private String address2;

    private String address21;

    private String corpeng;

    private String zipcdeng;

    private String addresseng;

    private String addresseng1;

    private String corpregno;

    private String bsns;

    private String item;

    private MultipartFile logonm;

    private MultipartFile stampnm;
}
