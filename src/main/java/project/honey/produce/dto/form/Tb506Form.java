package project.honey.produce.dto.form;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.Tb505_1Dto;
import project.honey.produce.dto.Tb506_1Dto;
import project.honey.produce.entity.Tb505;
import project.honey.produce.entity.Tb506;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb506Form {

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
    private List<Tb506_1Dto> tb506_1Dtos;

    public static Tb506Form of(Tb506 tb506, List<Tb506_1Dto> dtoList) {
        return Tb506Form.builder()
                .seq(tb506.getSeq())
                .wHouseDt(GlobalMethod.makeYmd(tb506.getWHouseDt(), "yyyy-MM-dd"))
                .wHouseNo(tb506.getWHouseNo())
                .empNo(tb506.getEmpNo())
                .wHouseIn(tb506.getWHouseIn())
                .wHouseOut(tb506.getWHouseOut())
                .projectCd(tb506.getProjectCd())
                .status(tb506.getStatus())
                .closeYn(tb506.getCloseYn())
                .note(tb506.getNote())
                .tb506_1Dtos(dtoList)
                .build();
    }
}
