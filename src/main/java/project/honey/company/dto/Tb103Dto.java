package project.honey.company.dto;

import lombok.*;


@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tb103Dto {

    private Integer seq;

    private Integer fk_tb_101;

    private String cernm;

    private String expdt;

    private String usenote;

    private String savemtd;

    private String empno;

    private String useyn;

    private String note;

    private String createDate;

    private String createId;

    private String updateDate;

    private String updateId;

    public void beforeProcess(){
        this.expdt = expdt.replace("-","");
        if(useyn == null) useyn = "N";
    }
}
