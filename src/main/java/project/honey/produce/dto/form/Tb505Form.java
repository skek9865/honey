package project.honey.produce.dto.form;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.Tb505_1Dto;
import project.honey.produce.entity.Tb505;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb505Form {

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
    private List<Tb505_1Dto> tb505_1Dtos;

    public static Tb505Form of(Tb505 tb505, List<Tb505_1Dto> dtoList) {
        return Tb505Form.builder()
                .seq(tb505.getSeq())
                .wHouseDt(GlobalMethod.makeYmd(tb505.getWHouseDt(), "yyyy-MM-dd"))
                .wHouseNo(tb505.getWHouseNo())
                .empNo(tb505.getEmpNo())
                .wHouseIn(tb505.getWHouseIn())
                .wHouseOut(tb505.getWHouseOut())
                .projectCd(tb505.getProjectCd())
                .status(tb505.getStatus())
                .closeYn(tb505.getCloseYn())
                .note(tb505.getNote())
                .tb505_1Dtos(dtoList)
                .build();
    }
}
