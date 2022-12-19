package project.honey.company.entity;

import lombok.*;
import project.honey.comm.BaseAtt;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
@Getter
@Table(name = "tb_105_1")
public class Tb105_1 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Integer seq;

    @Column(name = "fk_tb_101")
    private Integer fk_tb_101;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="fk_tb_105")
    private Tb105 fk_tb_105;

    @Column(name = "instdt")
    private String instdt;

    @Column(name = "instamt")
    private Integer instamt;

    @Column(name = "note")
    private String note;
}
