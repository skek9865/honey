package project.honey.company.dto;

import lombok.*;
import project.honey.comm.GlobalMethod;
import project.honey.company.entity.Tb107;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Tb107Dto {

    private Integer seq;
    private Integer tb101;
    private String carNm;
    private String carYear;
    private String carNo;
    private String empNo;
    private String instNm;
    private String instSDt;
    private String instEDt;
    private String ageLimit;
    private Integer instAmt;
    private String note;
    private String createDate;
    private String createId;
    private String updateDate;
    private String updateId;

    public Tb107Dto(String date){
        this.tb101 = 27;
        this.instSDt = date;
        this.instEDt = date;
        this.instAmt = 0;
    }

    public static Tb107Dto of(Tb107 entity, String empNm){
        if(empNm == null) empNm = entity.getEmpNo();
        String instSDt = entity.getInstSDt().substring(0, 4) + "-" + entity.getInstSDt().substring(4, 6) + "-" + entity.getInstSDt().substring(6, 8);
        String instEDt = entity.getInstEDt().substring(0, 4) + "-" + entity.getInstEDt().substring(4, 6) + "-" + entity.getInstEDt().substring(6, 8);

        return Tb107Dto.builder()
                .seq(entity.getSeq())
                .tb101(entity.getTb101())
                .carNm(entity.getCarNm())
                .carNo(entity.getCarNo())
                .carYear(entity.getCarYear())
                .empNo(empNm)
                .instNm(entity.getInstNm())
                .instSDt(instSDt)
                .instEDt(instEDt)
                .ageLimit(entity.getAgeLimit())
                .instAmt(entity.getInstAmt())
                .note(entity.getNote())
                .createDate(entity.getCreateDate())
                .createId(entity.getCreateId())
                .updateDate(entity.getUpdateDate())
                .updateId(entity.getUpdateId())
                .build();
    }

    public static Tb107 toTb107(Tb107Dto dto){
        String instSDt = GlobalMethod.replaceYmd(dto.getInstSDt(), "-");
        String instEDt = GlobalMethod.replaceYmd(dto.getInstEDt(), "-");

        return Tb107.builder()
                .tb101(dto.getTb101())
                .carNm(dto.getCarNm())
                .carNo(dto.getCarNo())
                .carYear(dto.getCarYear())
                .empNo(dto.getEmpNo())
                .instNm(dto.getInstNm())
                .instSDt(instSDt)
                .instEDt(instEDt)
                .ageLimit(dto.getAgeLimit())
                .instAmt(dto.getInstAmt())
                .note(dto.getNote())
                .build();
    }
}
