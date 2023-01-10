package project.honey.stock.dto.form;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.stock.dto.Tb601_1Dto;
import project.honey.stock.dto.Tb602_1Dto;
import project.honey.stock.entity.Tb601;
import project.honey.stock.entity.Tb602;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb602Form {

    private Integer seq;
    private String wHouseDt;
    private Integer wHouseNo;
    private String custCd;
    private String custNm;
    private String empNo;
    private String wHouseOut;
    private String projectCd;
    private String status;
    private String closeYn;
    private String note;
    private List<Tb602_1Dto> tb602_1Dtos;

    public static Tb602Form of(Tb602 tb602, List<Tb602_1Dto> dtoList, Map<String, String> custMap) {
        return Tb602Form.builder()
                .seq(tb602.getSeq())
                .wHouseDt(GlobalMethod.makeYmd(tb602.getWHouseDt(), "yyyy-MM-dd"))
                .wHouseNo(tb602.getWHouseNo())
                .empNo(tb602.getEmpNo())
                .custCd(tb602.getCustCd())
                .custNm(custMap.get(tb602.getCustCd()))
                .wHouseOut(tb602.getWHouseOut())
                .projectCd(tb602.getProjectCd())
                .status(tb602.getStatus())
                .closeYn(tb602.getCloseYn())
                .note(tb602.getNote())
                .tb602_1Dtos(dtoList)
                .build();
    }
}
