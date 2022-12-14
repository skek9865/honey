package project.honey.company.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tb101Dto {

    private Integer seq;

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

    private String logonm;

    private String stampnm;

    private String createDate;

    private String createId;

    private String updateDate;

    private String updateId;
}
