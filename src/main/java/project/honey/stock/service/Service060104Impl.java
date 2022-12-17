package project.honey.stock.service;

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
import project.honey.produce.service.Service050303;
import project.honey.stock.dto.Dto060104;
import project.honey.stock.dto.Tb601_1Dto;
import project.honey.stock.dto.search.Search060101;
import project.honey.stock.entity.Tb601;
import project.honey.stock.repository.Tb601Repository;
import project.honey.stock.repository.Tb601_1Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service060104Impl implements Service060104 {

    private final Tb601Repository tb601Repository;
    private final Tb601_1Repository tb601_1Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto060104> findAll(Search060101 search, Pageable pageable) {

        // 날짜 부호 빼주기
        Search060101 realSearch = new Search060101(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), null);

        Page<Tb601> pagingTb601s = tb601Repository.findAllByDsl(realSearch, pageable);
        List<Tb601> tb601s = pagingTb601s.getContent();

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();

        List<Dto060104> dtoList = new ArrayList<>();
        // 화면에 출력할 데이터 생성
        for (Tb601 tb601 : tb601s) {
            List<Tb601_1Dto> tb601_1Dtos = tb601_1Repository.findAllByDtos(tb601.getSeq());

            // 정렬
            tb601_1Dtos.sort(Comparator.comparing(Tb601_1Dto::getSeq));

            // 대표품목명
            String goodsNm = tb601_1Dtos.get(0).getGoodsNm();
            int cnt = 0;
            int wPrice = 0;
            int qty = 0;

            for (Tb601_1Dto dto : tb601_1Dtos) {
                qty += dto.getQty();
                wPrice += dto.getQty() * dto.getWPrice();
                cnt++;
            }
            if (cnt > 1) goodsNm += "외" + (cnt - 1) + "건";
            Dto060104 dto = Dto060104.of(tb601, goodsNm, qty,wPrice, wHouseMap);
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pagingTb601s.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search060101 search) {
        // 날짜 부호 빼주기
        Search060101 realSearch = new Search060101(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), null);

        List<Tb601> tb601s = tb601Repository.findAllByExcel(realSearch);

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();

        List<List<String>> excelList = new ArrayList<>();
        int tQty = 0;
        int tWPrice = 0;
        // 화면에 출력할 데이터 생성
        for (Tb601 tb601 : tb601s) {
            List<String> list = new ArrayList<>();
            List<Tb601_1Dto> tb601_1Dtos = tb601_1Repository.findAllByDtos(tb601.getSeq());

            // 정렬
            tb601_1Dtos.sort(Comparator.comparing(Tb601_1Dto::getSeq));

            // 대표품목명
            String goodsNm = tb601_1Dtos.get(0).getGoodsNm();
            int cnt = 0;
            int wPrice = 0;
            int qty = 0;

            for (Tb601_1Dto dto : tb601_1Dtos) {
                qty += dto.getQty();
                wPrice += dto.getQty() * dto.getWPrice();
                cnt++;
            }
            if (cnt > 1) goodsNm += "외" + (cnt - 1) + "건";

            // 데이터 추가 부분
            list.add(GlobalMethod.makeYmd(tb601.getWHouseDt(), "yyyy-MM-dd") + "-" + tb601.getWHouseNo());
            list.add(wHouseMap.get(tb601.getWHouseOut()));
            list.add(wHouseMap.get(tb601.getWHouseIn()));
            list.add(goodsNm);
            list.add(String.valueOf(qty));
            list.add(String.valueOf(wPrice));
            list.add(tb601.getNote());

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
