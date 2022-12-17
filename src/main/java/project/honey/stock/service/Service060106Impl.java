package project.honey.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;
import project.honey.stock.dto.Dto060105;
import project.honey.stock.dto.Dto060106;
import project.honey.stock.dto.Tb602_1Dto;
import project.honey.stock.dto.Tb603_1Dto;
import project.honey.stock.dto.search.Search060102;
import project.honey.stock.dto.search.Search060103;
import project.honey.stock.entity.Tb602;
import project.honey.stock.entity.Tb603;
import project.honey.stock.repository.Tb603Repository;
import project.honey.stock.repository.Tb603_1Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service060106Impl implements Service060106 {

    private final Tb603Repository tb603Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto060106> findAll(Search060103 search, Pageable pageable) {

        // 날짜 부호 빼주기
        Search060103 realSearch = new Search060103(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), null);

        Page<Tb603> pagingTb603s = tb603Repository.findAllByDsl(realSearch, pageable);
        List<Tb603> tb603s = pagingTb603s.getContent();

        // 데이터 변환에 필요한 map
        Map<String, String> prcsmTdMap = codeToName.commonCode("16");
        Map<String, String> goodsMap = codeToName.goods();

        List<Dto060106> dtoList = new ArrayList<>();
        // 화면에 출력할 데이터 생성
        for (Tb603 tb603 : tb603s) {
            List<Tb603_1Dto> tb603_1Dtos = tb603.getTb603_1s().stream()
                    .map(m -> Tb603_1Dto.of(m, tb603, goodsMap))
                    .sorted(Comparator.comparing(Tb603_1Dto::getSeq))
                    .collect(Collectors.toList());

            // 대표품목명
            String goodsNm = tb603_1Dtos.get(0).getGoodsNm();
            int cnt = 0;
            int qty = 0;

            for (Tb603_1Dto dto : tb603_1Dtos) {
                qty += dto.getQty();
                cnt++;
            }
            if (cnt > 1) goodsNm += "외" + (cnt - 1) + "건";
            Dto060106 dto = Dto060106.of(tb603, goodsNm, qty,prcsmTdMap);
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pagingTb603s.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search060103 search) {
        // 날짜 부호 빼주기
        Search060103 realSearch = new Search060103(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), null);

        List<Tb603> tb603s = tb603Repository.findAllByExcel(realSearch);

        // 데이터 변환에 필요한 map
        Map<String, String> prcsmTdMap = codeToName.commonCode("16");
        Map<String, String> goodsMap = codeToName.goods();

        List<List<String>> excelList = new ArrayList<>();
        int tQty = 0;
        // 화면에 출력할 데이터 생성
        for (Tb603 tb603 : tb603s) {
            List<String> list = new ArrayList<>();
            List<Tb603_1Dto> tb603_1Dtos = tb603.getTb603_1s().stream()
                    .map(m -> Tb603_1Dto.of(m, tb603, goodsMap))
                    .sorted(Comparator.comparing(Tb603_1Dto::getSeq))
                    .collect(Collectors.toList());

            // 대표품목명
            String goodsNm = tb603_1Dtos.get(0).getGoodsNm();
            int cnt = 0;
            int qty = 0;

            for (Tb603_1Dto dto : tb603_1Dtos) {
                qty += dto.getQty();
                cnt++;
            }
            if (cnt > 1) goodsNm += "외" + (cnt - 1) + "건";

            // 데이터 추가 부분
            list.add(GlobalMethod.makeYmd(tb603.getWHouseDt(), "yyyy-MM-dd") + "-" + tb603.getWHouseNo());
            list.add(prcsmTdMap.get(tb603.getPrcsmTd()));
            list.add(goodsNm);
            list.add(String.valueOf(qty));
            list.add(tb603.getNote());

            excelList.add(list);

            tQty += qty;
        }

        // 총계
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("총계");
        list.add(String.valueOf(tQty));
        list.add("");
        excelList.add(list);

        return excelList;
    }
}
