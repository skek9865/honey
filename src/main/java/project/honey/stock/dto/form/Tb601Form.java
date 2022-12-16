package project.honey.stock.dto.form;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.Tb505_1Dto;
import project.honey.produce.dto.form.Tb505Form;
import project.honey.produce.entity.Tb505;
import project.honey.stock.dto.Tb601_1Dto;
import project.honey.stock.entity.Tb601;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb601Form {

    private Integer seq;
    private String wHouseDt;
    private Integer wHouseNo;
    private String empNo;
    private String wHouseIn;
    private String wHouseOut;
    private String projectCd;
    private String status;
    private String closeYn;
    private String note;
    private List<Tb601_1Dto> tb601_1Dtos;

    public static Tb601Form of(Tb601 tb601, List<Tb601_1Dto> dtoList) {
        return Tb601Form.builder()
                .seq(tb601.getSeq())
                .wHouseDt(GlobalMethod.makeYmd(tb601.getWHouseDt(), "yyyy-MM-dd"))
                .wHouseNo(tb601.getWHouseNo())
                .empNo(tb601.getEmpNo())
                .wHouseIn(tb601.getWHouseIn())
                .wHouseOut(tb601.getWHouseOut())
                .projectCd(tb601.getProjectCd())
                .status(tb601.getStatus())
                .closeYn(tb601.getCloseYn())
                .note(tb601.getNote())
                .tb601_1Dtos(dtoList)
                .build();
    }
}
