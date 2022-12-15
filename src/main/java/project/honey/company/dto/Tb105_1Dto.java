package project.honey.company.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
public class Tb105_1Dto {

    private Integer seq;

    private Integer fk_tb_101;

    private Integer fk_tb_105;

    private String instdt;

    private Integer instamt;

    private String note;

    private String createDate;

    private String createId;

    private String updateDate;

    private String updateId;
}
