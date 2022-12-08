package project.honey.company.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb104Dto {

    private Integer seq;

    private Integer fk_tb_101;

    private String cardnm;

    private String cardno;

    private String expdt;

    private String cvcno;

    private String useyn;

    private String empno;

    private Integer fk_tb_102;

    private Integer limitamt;

    private String issuedt;

    private Integer fk_tb_103;

    private String note;

    private String createDate;

    private String createId;

    private String updateDate;

    private String updateId;

    public void beforeProcess(){
        this.expdt = expdt.replace("-","");
        this.issuedt = issuedt.replace("-","");
        if(useyn == null) useyn = "N";
    }
}
