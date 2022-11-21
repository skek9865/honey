package project.honey.pay.dto;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import project.honey.pay.entity.Tb301;
import project.honey.personDepart.entity.Tb201;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class Tb302Dto {

    private Integer seq;

    private String empNm;

    private String post;

    private String itemDiv;

    private String taxDiv;

    private Integer itemCd;

    private Double payAmt;


}
