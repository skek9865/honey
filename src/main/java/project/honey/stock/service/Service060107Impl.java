package project.honey.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import project.honey.business.entity.basic.Tb405;
import project.honey.business.repository.basic.Tb405Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.Dto050104;
import project.honey.produce.entity.Tb503;
import project.honey.produce.entity.Tb503_1;
import project.honey.stock.dto.Dto060104;
import project.honey.stock.dto.Dto060107;
import project.honey.stock.dto.Tb603_1Dto;
import project.honey.stock.dto.search.Search060103;
import project.honey.stock.dto.search.Search060107;
import project.honey.stock.entity.Tb603;
import project.honey.stock.entity.Tb603_1;
import project.honey.stock.repository.Tb603_1Repository;
import project.honey.stock.repository.Tb603_1RepositoryDsl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service060107Impl implements Service060107 {

    private final Tb405Repository tb405Repository;
    private final Tb603_1Repository tb603_1Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto060107> findAll(Search060107 search, Pageable pageable) {

        // 날짜 부호 빼주기
        Search060107 realSearch = new Search060107(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"));

        // 필요한 데이터 세팅
        Page<Tb603_1> pagingTb603_1s = tb603_1Repository.findAllByDsl(realSearch,pageable);
        List<Tb603_1> tb603_1s = pagingTb603_1s.getContent();
        Map<String, String> prcsmTdMap = codeToName.commonCode("16");
        Map<String, String> goodsMap = codeToName.goods();

        List<Dto060107> dtoList = new ArrayList<>();
        // dtoList 생성
        for (Tb603_1 tb603_1 : tb603_1s) {
            Dto060107 dto = Dto060107.of(tb603_1, prcsmTdMap, goodsMap);
            dtoList.add(dto);
        }

        // 리스트 정렬
        dtoList.sort(Comparator.comparing(Dto060107::getSeq));

        // 겹치는 Tb603 값 비워주기
        int seq = -1;
        List<Dto060107> result = new ArrayList<>();
        for (Dto060107 dto : dtoList) {
            if (dto.getSeq() != seq) {
                seq = dto.getSeq();
            } else{
                dto.setWHouseNo("");
            }
            result.add(dto);
        }

        return new PageImpl<>(result, pageable, pagingTb603_1s.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search060107 search) {
        // 날짜 부호 빼주기
        Search060107 realSearch = new Search060107(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"));

        // 필요한 데이터 세팅
        List<Tb603_1> tb603_1s = tb603_1Repository.findAllByExcel(realSearch);
        Map<String, String> prcsmTdMap = codeToName.commonCode("16");
        Map<String, String> goodsMap = codeToName.goods();

        List<Dto060107> dtoList = new ArrayList<>();
        // dtoList 생성
        for (Tb603_1 tb603_1 : tb603_1s) {
            Dto060107 dto = Dto060107.of(tb603_1, prcsmTdMap, goodsMap);
            dtoList.add(dto);
        }

        // 리스트 정렬
        dtoList.sort(Comparator.comparing(Dto060107::getSeq));

        // 겹치는 Tb603 값 비워주기
        int seq = -1;
        List<Dto060107> result = new ArrayList<>();
        for (Dto060107 dto : dtoList) {
            if (dto.getSeq() != seq) {
                seq = dto.getSeq();
            } else{
                dto.setWHouseNo("");
            }
            result.add(dto);
        }

        // 엑셀 변환
        List<List<String>> excelList = new ArrayList<>();
        int totalQty = 0;
        int totalFQty = 0;
        for (Dto060107 dto : result) {
            List<String> list = new ArrayList<>();
            list.add(dto.getWHouseNo());
            list.add(dto.getPrcsmTd());
            list.add(dto.getGoodsNm());
            list.add(dto.getQty() != null ? String.valueOf(dto.getQty()) : "");
            list.add(dto.getGoodsNms());
            list.add(dto.getRQty() != null ? String.valueOf(dto.getRQty()) : "");
            list.add(dto.getNote());
            excelList.add(list);
            totalQty += dto.getQty() != null ? dto.getQty() : 0;
            totalFQty += dto.getRQty() != null ? dto.getRQty() : 0;
        }
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("총계");
        list.add(String.valueOf(totalQty));
        list.add("총계");
        list.add(String.valueOf(totalFQty));
        list.add("");
        excelList.add(list);

        return excelList;
    }
}
