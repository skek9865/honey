package project.honey.produce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.Dto050303;
import project.honey.produce.dto.Tb506_1Dto;
import project.honey.produce.dto.search.Search050302;
import project.honey.produce.entity.Tb506;
import project.honey.produce.repository.Tb506Repository;
import project.honey.produce.repository.Tb506_1Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service050303Impl implements Service050303{

    private final Tb506Repository tb506Repository;
    private final Tb506_1Repository tb506_1Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto050303> findAll(Search050302 search, Pageable pageable) {

        // 날짜 부호 빼주기
        Search050302 realSearch = new Search050302(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), null);

        Page<Tb506> pagingTb506s = tb506Repository.findAllByDsl(realSearch, pageable);
        List<Tb506> tb506s = pagingTb506s.getContent();

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();

        List<Dto050303> dtoList = new ArrayList<>();
        // 화면에 출력할 데이터 생성
        for (Tb506 tb506 : tb506s) {
            List<Tb506_1Dto> tb506_1Dtos = tb506_1Repository.findAllByDtos(tb506.getSeq());

            // 정렬
            tb506_1Dtos.sort(Comparator.comparing(Tb506_1Dto::getSeq));

            // 대표품목명
            String goodsNm = tb506_1Dtos.get(0).getGoodsNm();
            int cnt = 0;
            int wPrice = 0;
            int qty = 0;

            for (Tb506_1Dto dto : tb506_1Dtos) {
                qty += dto.getQty();
                wPrice += dto.getQty() * dto.getWPrice();
                cnt++;
            }
            if (cnt > 1) goodsNm += "외" + (cnt - 1) + "건";
            Dto050303 dto = Dto050303.of(tb506, goodsNm, qty,wPrice, wHouseMap);
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pagingTb506s.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search050302 search) {
        // 날짜 부호 빼주기
        Search050302 realSearch = new Search050302(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), null);

        List<Tb506> tb506s = tb506Repository.findAllByExcel(realSearch);

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();

        List<List<String>> excelList = new ArrayList<>();
        int tQty = 0;
        int tWPrice = 0;
        // 화면에 출력할 데이터 생성
        for (Tb506 tb506 : tb506s) {
            List<String> list = new ArrayList<>();
            List<Tb506_1Dto> tb506_1Dtos = tb506_1Repository.findAllByDtos(tb506.getSeq());

            // 정렬
            tb506_1Dtos.sort(Comparator.comparing(Tb506_1Dto::getSeq));

            // 대표품목명
            String goodsNm = tb506_1Dtos.get(0).getGoodsNm();
            int cnt = 0;
            int wPrice = 0;
            int qty = 0;

            for (Tb506_1Dto dto : tb506_1Dtos) {
                qty += dto.getQty();
                wPrice += dto.getQty() * dto.getWPrice();
                cnt++;
            }
            if (cnt > 1) goodsNm += "외" + (cnt - 1) + "건";

            // 데이터 추가 부분
            list.add(GlobalMethod.makeYmd(tb506.getWHouseDt(), "yyyy-MM-dd") + "-" + tb506.getWHouseNo());
            list.add(wHouseMap.get(tb506.getWHouseOut()));
            list.add(wHouseMap.get(tb506.getWHouseIn()));
            list.add(goodsNm);
            list.add(String.valueOf(qty));
            list.add(String.valueOf(wPrice));
            list.add(tb506.getNote());

            excelList.add(list);

            tQty += qty;
            tWPrice += wPrice;
        }

        // 총계
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("총계");
        list.add(String.valueOf(tQty));
        list.add(String.valueOf(tWPrice));
        list.add("");
        excelList.add(list);

        return excelList;
    }
}
