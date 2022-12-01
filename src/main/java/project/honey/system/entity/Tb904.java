package project.honey.system.entity;

import lombok.*;
import project.honey.comm.BaseAtt;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table(name = "tb_904")
@ToString
public class Tb904 extends BaseAtt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(name = "fstid", columnDefinition = "char")
    private String fstId;

    @Column(name = "scdid", columnDefinition = "char")
    private String scdId;

    @Column(name = "thdid", columnDefinition = "char")
    private String thdId;

    private Integer alien;

    @Column(name = "menunm")
    private String menuNm;

    @Column(name = "menuurl")
    private String menuUrl;

    @Column(name = "useyn", columnDefinition = "char")
    private String useYn;

}
