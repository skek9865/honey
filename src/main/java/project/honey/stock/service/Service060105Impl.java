package project.honey.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;
import project.honey.stock.dto.Dto060104;
import project.honey.stock.dto.Dto060105;
import project.honey.stock.dto.Tb601_1Dto;
import project.honey.stock.dto.Tb602_1Dto;
import project.honey.stock.dto.search.Search060101;
import project.honey.stock.dto.search.Search060102;
import project.honey.stock.entity.Tb601;
import project.honey.stock.entity.Tb602;
import project.honey.stock.repository.Tb602Repository;
import project.honey.stock.repository.Tb602_1Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service060105Impl implements Service060105 {

    private final Tb602Repository tb602Repository;
    private final Tb602_1Repository tb602_1Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto060105> findAll(Search060102 search, Pageable pageable) {

        // 날짜 부호 빼주기
        Search060102 realSearch = new Search060102(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), null);

        Page<Tb602> pagingTb602s = tb602Repository.findAllByDsl(realSearch, pageable);
        List<Tb602> tb602s = pagingTb602s.getContent();

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> custMap = codeToName.cust();

        List<Dto060105> dtoList = new ArrayList<>();
        // 화면에 출력할 데이터 생성
        for (Tb602 tb602 : tb602s) {
            List<Tb602_1Dto> tb602_1Dtos = tb602_1Repository.findAllByDtos(tb602.getSeq());

            // 정렬
            tb602_1Dtos.sort(Comparator.comparing(Tb602_1Dto::getSeq));

            // 대표품목명
            String goodsNm = tb602_1Dtos.get(0).getGoodsNm();
            int cnt = 0;
            int wPrice = 0;
            int qty = 0;

            for (Tb602_1Dto dto : tb602_1Dtos) {
                qty += dto.getQty();
                wPrice += dto.getQty() * dto.getWPrice();
                cnt++;
            }
            if (cnt > 1) goodsNm += "외" + (cnt - 1) + "건";
            Dto060105 dto = Dto060105.of(tb602, goodsNm, qty,wPrice, wHouseMap, custMap);
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pagingTb602s.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search060102 search) {
        // 날짜 부호 빼주기
        Search060102 realSearch = new Search060102(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), null);

        List<Tb602> tb602s = tb602Repository.findAllByExcel(realSearch);

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> custMap = codeToName.cust();

        List<List<String>> excelList = new ArrayList<>();
        int tQty = 0;
        int tWPrice = 0;
        // 화면에 출력할 데이터 생성
        for (Tb602 tb602 : tb602s) {
            List<String> list = new ArrayList<>();
            List<Tb602_1Dto> tb602_1Dtos = tb602_1Repository.findAllByDtos(tb602.getSeq());

            // 정렬
            tb602_1Dtos.sort(Comparator.comparing(Tb602_1Dto::getSeq));

            // 대표품목명
            String goodsNm = tb602_1Dtos.get(0).getGoodsNm();
            int cnt = 0;
            int wPrice = 0;
            int qty = 0;

            for (Tb602_1Dto dto : tb602_1Dtos) {
                qty += dto.getQty();
                wPrice += dto.getQty() * dto.getWPrice();
                cnt++;
            }
            if (cnt > 1) goodsNm += "외" + (cnt - 1) + "건";

            // 데이터 추가 부분
            list.add(GlobalMethod.makeYmd(tb602.getWHouseDt(), "yyyy-MM-dd") + "-" + tb602.getWHouseNo());
            list.add(custMap.get(tb602.getCustCd()));
            list.add(goodsNm);
            list.add(wHouseMap.get(tb602.getWHouseOut()));
            list.add(String.valueOf(qty));
            list.add(String.valueOf(wPrice));
            list.add(tb602.getNote());

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
