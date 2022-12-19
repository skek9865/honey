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

    private String instdt;

    private Integer instamt;

    //잔액
    private Integer restamt;

    private String note;

}
