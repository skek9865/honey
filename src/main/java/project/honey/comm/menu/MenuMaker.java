package project.honey.comm.menu;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class MenuMaker {
    private final MenuRepository menuRepository;

    public List<MenuIdDto> getMenuId(Integer type, String fcd, String scd, String userId){
        List<Tb904> menuId = menuRepository.getMenuId(type, fcd, scd, userId);

        List<MenuIdDto> result = menuId.stream().map(tb904 ->
            (MenuIdDto.builder()
                    .fstId(tb904.getFstId())
                    .scdId(tb904.getScdId())
                    .thdId(tb904.getThdId())
                    .menuNm(tb904.getMenuNm())
                    .build())
        ).collect(Collectors.toList());

        return result;
    }

    public MenuNm getMenuNm(QueryParam param){
        List<Tb904> menuNm = menuRepository.findMenuNm(param.getFstId());
        Optional<Tb904> fstNm = menuNm.stream().filter(entity -> (entity.getScdId().equals("00"))).findAny();
        Optional<Tb904> scdNm = menuNm.stream().filter(entity -> (entity.getScdId().equals(param.getScdId()))).findAny();
        Optional<Tb904> thdNm = menuNm.stream().filter(entity -> (entity.getScdId().equals(param.getScdId()))).filter(entity -> (entity.getThdId().equals(param.getThdId()))).findAny();
        if(fstNm != null && scdNm != null && thdNm != null){
            return MenuNm.builder()
                    .fstNm(fstNm.get().getMenuNm())
                    .scdNm(scdNm.get().getMenuNm())
                    .thdNm(thdNm.get().getMenuNm())
                    .build();
        }
        return null;

    }
}
