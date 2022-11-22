package project.honey.pay.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tb302SubDto {

    private Integer seq;

    private String empNo;

    private String empNm;

    private String empDt;

    private String post;

    private String deptNm;

    @QueryProjection
    public Tb302SubDto(Integer seq, String empNo, String empNm, String empDt, String post, String deptNm) {
        this.seq = seq;
        this.empNo = empNo;
        this.empNm = empNm;
        this.empDt = empDt;
        this.post = post;
        this.deptNm = deptNm;
    }
}
