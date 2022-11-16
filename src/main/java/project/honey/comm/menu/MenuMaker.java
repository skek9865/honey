package project.honey.comm.menu;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MenuMaker {
    private final MenuRepository menuRepository;

    public List<MenuIdDto> getMenuId(Integer type, String fcd, String scd, String userId){
        List<Tb904> menuId = menuRepository.getMenuId(type, fcd, scd, userId);

        List<MenuIdDto> result = menuId.stream().map(tb904 -> {
            return MenuIdDto.builder()
                    .fstId(tb904.getFstId())
                    .scdId(tb904.getScdId())
                    .thdId(tb904.getThdId())
                    .menuNm(tb904.getMenuNm())
                    .build();
        }).collect(Collectors.toList());

        return result;
    }
}
