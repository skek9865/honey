package project.honey.system.dto;

import lombok.*;
import org.hibernate.annotations.Comment;
import project.honey.system.entity.Tb903;
import project.honey.system.entity.Tb904;
import project.honey.system.entity.Tb905;

import javax.persistence.Column;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Tb905Dto {

    private Integer seq;

    private String menuGbCd;

    private String menuGbNm;

    private String tpId;

    private String menuYn;

    private String listYn;

    private String viewYn;

    private String saveYn;

    private String modifyYn;

    private String delYn;

    //임시 기본값 설정
    public void setMenuYn(String menuYn) {
        this.menuYn = menuYn !=null?menuYn:"N";
    }

    public void setListYn(String listYn) {
        this.listYn = listYn !=null?listYn:"N";
    }

    public void setViewYn(String viewYn) {
        this.viewYn = viewYn !=null?viewYn:"N";
    }

    public void setSaveYn(String saveYn) {
        this.saveYn = saveYn != null?saveYn:"N";
    }

    public void setModifyYn(String modifyYn) {
        this.modifyYn = modifyYn!=null?modifyYn:"N";
    }
    public void setDelYn(String delYn) {
        this.delYn = delYn!=null?delYn:"N";
    }

    public static Tb905Dto of(Tb905 entity,Map<String, String> menuGbMap, Map<String, String> screenMap) {

        return Tb905Dto.builder()
                .seq(entity.getSeq())
                .menuGbCd(menuGbMap.get(entity.getMenuGbCd()))
                .tpId(screenMap.get(entity.getTpId()))
                .menuYn(entity.getMenuYn())
                .listYn(entity.getListYn())
                .viewYn(entity.getViewYn())
                .saveYn(entity.getSaveYn())
                .modifyYn(entity.getModifyYn())
                .delYn(entity.getDelYn())
                .build();
    }

    public static Tb905 toTb905(Tb905Dto dto, Tb904 tb904) {
        return Tb905.builder()
                .menuGbCd(dto.getMenuGbCd())
                .tpId(tb904.getFstId() + tb904.getScdId() + tb904.getThdId())
                .menuYn(dto.getMenuYn())
                .listYn(dto.getListYn())
                .viewYn(dto.getViewYn())
                .saveYn(dto.getSaveYn())
                .modifyYn(dto.getModifyYn())
                .delYn(dto.getDelYn())
                .build();
    }
}
