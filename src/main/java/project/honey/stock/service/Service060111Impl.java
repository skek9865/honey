package project.honey.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;
import project.honey.stock.dto.Dto060111;
import project.honey.stock.dto.search.Search060111;
import project.honey.stock.repository.Tb604Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service060111Impl implements Service060111{

    private final Tb604Repository tb604Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto060111> findAll(Search060111 search, Pageable pageable) {

        // 날짜 부호 빼주기
        Search060111 realSearch = new Search060111(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"));

        Map<String, String> wHouseMap = codeToName.wHouse();
        return tb604Repository.findAllByQuery(realSearch, pageable)
                .map(m -> Dto060111.of(m, wHouseMap));
    }

    @Override
    public List<List<String>> findAllByExcel(Search060111 search) {

        // 날짜 부호 빼주기
        Search060111 realSearch = new Search060111(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"));

        Map<String, String> wHouseMap = codeToName.wHouse();

        List<Dto060111> dtoList = tb604Repository.findAllByQueryExcel(realSearch).stream()
                .map(m -> Dto060111.of(m, wHouseMap))
                .collect(Collectors.toList());

        List<List<String>> excelList = new ArrayList<>();

        int stQty = 0;
        int reQty = 0;
        int adQty = 0;
        int wPrice = 0;
        for (Dto060111 dto : dtoList) {
            List<String> list = new ArrayList<>();
            list.add(dto.getWHouseNo());
            list.add(dto.getGoodsNm());
            list.add(dto.getWHouseOut());
            list.add(String.valueOf(dto.getStQty()));
            list.add(String.valueOf(dto.getReQty()));
            list.add(String.valueOf(dto.getAdQty()));
            list.add(String.valueOf(dto.getWPrice()));
            list.add(dto.getNote());

            excelList.add(list);
            stQty += dto.getStQty();
            reQty += dto.getReQty();
            adQty += dto.getAdQty();
            wPrice += dto.getWPrice();
        }
        //총계
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("총계");
        list.add(String.valueOf(stQty));
        list.add(String.valueOf(reQty));
        list.add(String.valueOf(adQty));
        list.add(String.valueOf(wPrice));
        list.add("");
        excelList.add(list);

        return excelList;
    }
}
