package project.honey.stock.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.Query506_1Dto;
import project.honey.produce.repository.Tb503_1Repository;
import project.honey.produce.repository.Tb506_1Repository;
import project.honey.stock.dto.Dto060108;
import project.honey.stock.dto.Dto060109;
import project.honey.stock.dto.Tb603_1Dto;
import project.honey.stock.dto.search.Search060103;
import project.honey.stock.dto.search.Search060109;
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
@Slf4j
public class Service060109Impl implements Service060109 {

    private final Tb506_1Repository tb506_1Repository;
    private final Tb603_1Repository tb603_1Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto060109> findAll(Search060109 search, Pageable pageable) {

        // 날짜 부호 빼주기
        Search060109 realSearch = new Search060109(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"));

        Page<Query506_1Dto> pagingQuery506_1Dtos = tb506_1Repository.findAllByQuery(realSearch, pageable);
        List<Query506_1Dto> query506_1Dtos = pagingQuery506_1Dtos.getContent();
        log.info("dtos = {}", query506_1Dtos);

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> goodsMap = codeToName.goods();

        List<Dto060109> dtoList = new ArrayList<>();
        // 화면에 출력할 데이터 생성
        for (Query506_1Dto queryDto : query506_1Dtos) {
            // 불량갯수
            Integer rQty = tb603_1Repository.findQtyByGoodsCd(queryDto.getGoodsCd());
            Dto060109 dto = Dto060109.of(queryDto, rQty, wHouseMap, goodsMap);
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pagingQuery506_1Dtos.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search060109 search) {
        // 날짜 부호 빼주기
        Search060109 realSearch = new Search060109(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"));

        List<Query506_1Dto> query506_1Dtos = tb506_1Repository.findAllByExcel(realSearch);

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> goodsMap = codeToName.goods();

        List<List<String>> excelList = new ArrayList<>();
        // 화면에 출력할 데이터 생성
        int tQty = 0;
        int tRQty = 0;
        for (Query506_1Dto queryDto : query506_1Dtos) {
            // 불량갯수
            Integer rQty = tb603_1Repository.findQtyByGoodsCd(queryDto.getGoodsCd());
            Dto060109 dto = Dto060109.of(queryDto, rQty, wHouseMap, goodsMap);

            List<String> list = new ArrayList<>();
            list.add(dto.getWHouseNm());
            list.add(dto.getGoodsNm());
            list.add(String.valueOf(dto.getQty()));
            list.add(String.valueOf(dto.getRQty()));
            list.add(String.format("%.3f",dto.getRate()));

            excelList.add(list);

            tQty += dto.getQty();
            tRQty += dto.getRQty();
        }

        // 총계
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("총계");
        list.add(String.valueOf(tQty));
        list.add(String.valueOf(tRQty));
        list.add("");
        excelList.add(list);

        return excelList;
    }
}
