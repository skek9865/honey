package project.honey.stock.dto.form;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.stock.dto.Tb601_1Dto;
import project.honey.stock.dto.Tb603_1Dto;
import project.honey.stock.entity.Tb601;
import project.honey.stock.entity.Tb603;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb603Form {

    private Integer seq;
    private String wHouseDt;
    private Integer wHouseNo;
    private String empNo;
    private String wHouseOut;
    private String prcsmTd;
    private String projectCd;
    private String status;
    private String closeYn;
    private String note;
    private List<Tb603_1Dto> tb603_1Dtos;

    public static Tb603Form of(Tb603 tb603, List<Tb603_1Dto> dtoList) {
        return Tb603Form.builder()
                .seq(tb603.getSeq())
                .wHouseDt(GlobalMethod.makeYmd(tb603.getWHouseDt(), "yyyy-MM-dd"))
                .wHouseNo(tb603.getWHouseNo())
                .empNo(tb603.getEmpNo())
                .wHouseOut(tb603.getWHouseOut())
                .prcsmTd(tb603.getPrcsmTd())
                .projectCd(tb603.getProjectCd())
                .status(tb603.getStatus())
                .closeYn(tb603.getCloseYn())
                .note(tb603.getNote())
                .tb603_1Dtos(dtoList)
                .build();
    }
}
