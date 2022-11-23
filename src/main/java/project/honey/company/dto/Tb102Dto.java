package project.honey.company.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tb102Dto {
    private Integer seq;

    private Integer fk_tb_101;

    private String accountno;

    private String banknm;

    private String accounhd;

    private String usenote;

    private String accountid;

    private String stdate;

    private String note;

    private String note1;

    private String useyn;

    private String createDate;

    private String createId;

    private String updateDate;

    private String updateId;

    public void beforeProcess(){
        this.stdate = stdate.replace("-","");
        if(useyn == null) useyn = "N";
    }
}
