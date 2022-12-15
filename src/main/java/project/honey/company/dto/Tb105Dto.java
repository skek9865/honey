package project.honey.company.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tb105Dto {

    private Integer seq;

    private Integer fk_tb_101;

    private String loannm;

    private String fk_tb_102;

    private String newdt;

    private String expdt;

    private Integer limitamt;

    private Integer instamt;

    private String ipaydt;

    private float irate;

    private String note;

    private String createDate;

    private String createId;

    private String updateDate;

    private String updateId;
    public void beforeProcess() {
        this.newdt = newdt.replace("-", "");
        this.expdt = expdt.replace("-", "");
    }

}
