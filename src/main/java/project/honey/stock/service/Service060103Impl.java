package project.honey.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;
import project.honey.stock.dto.Tb601Dto;
import project.honey.stock.dto.Tb601_1Dto;
import project.honey.stock.dto.Tb603Dto;
import project.honey.stock.dto.Tb603_1Dto;
import project.honey.stock.dto.form.Tb601Form;
import project.honey.stock.dto.form.Tb603Form;
import project.honey.stock.dto.search.Search060101;
import project.honey.stock.dto.search.Search060103;
import project.honey.stock.entity.Tb601;
import project.honey.stock.entity.Tb601_1;
import project.honey.stock.entity.Tb603;
import project.honey.stock.entity.Tb603_1;
import project.honey.stock.repository.Tb603Repository;
import project.honey.stock.repository.Tb603_1Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service060103Impl implements Service060103{

    private final Tb603Repository tb603Repository;
    private final Tb603_1Repository tb603_1Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Tb603Dto> findAll(Search060103 search, Pageable pageable) {

        // 날짜 부호 빼주기
        Search060103 realSearch = new Search060103(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), search.getStatus());

        Page<Tb603> pagingTb603s = tb603Repository.findAllByDsl(realSearch, pageable);
        List<Tb603> tb603s = pagingTb603s.getContent();

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> prcsmTdMap = codeToName.commonCode("16");
        Map<String, String> goodsMap = codeToName.goods();

        List<Tb603Dto> dtoList = new ArrayList<>();
        // 화면에 출력할 데이터 생성
        for (Tb603 tb603 : tb603s) {
            List<Tb603_1> tb603_1s = tb603.getTb603_1s();

            // 정렬
            tb603_1s.sort(Comparator.comparing(Tb603_1::getSeq));

            // 대표품목명
            String goodsNm = goodsMap.get(tb603_1s.get(0).getGoodsCd());
            int cnt = 0;
            int qty = 0;

            for (Tb603_1 tb603_1 : tb603_1s) {
                qty += tb603_1.getQty();
                cnt++;
            }
            if (cnt > 1) goodsNm += "외" + (cnt - 1) + "건";
            Tb603Dto dto = Tb603Dto.of(tb603.getSeq(), tb603, goodsNm, qty, wHouseMap, statusMap, prcsmTdMap);
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pagingTb603s.getTotalElements());
    }

    @Override
    public Tb603Form findById(Integer seq) {
        if(seq == null){
            String wHouseDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int wHouseNo = tb603Repository.countByWHouseDt(GlobalMethod.replaceYmd(wHouseDt, "-"));
            List<Tb603_1Dto> dtoList = new ArrayList<>();
            while (dtoList.size() < 5) {
                dtoList.add(null);
            }
            return Tb603Form.builder()
                    .wHouseDt(wHouseDt)
                    .wHouseNo(wHouseNo + 1)
                    .tb603_1Dtos(dtoList)
                    .build();
        }

        Map<String, String> goodsMap = codeToName.goods();

        Tb603 tb603 = tb603Repository.findById(seq).orElseThrow(RuntimeException::new);
        List<Tb603_1> tb603_1s = tb603.getTb603_1s();

        // 정렬
        tb603_1s.sort(Comparator.comparing(Tb603_1::getSeq));
        List<Tb603_1Dto> dtoList = tb603_1s.stream()
                .map(m -> Tb603_1Dto.of(m, tb603, goodsMap))
                .collect(Collectors.toList());

        while (dtoList.size() < 5) {
            dtoList.add(null);
        }

        return Tb603Form.of(tb603, dtoList);
    }

    @Override
    @Transactional
    public Integer insert(Tb603Form dto) {
        Tb603 tb603 = Tb603Dto.toTb603(dto);
        List<Tb603_1> tb603_1s = dto.getTb603_1Dtos().stream()
                .filter(f -> StringUtils.hasText(f.getGoodsCd()))
                .map(m -> Tb603_1Dto.toTb603_1(m, tb603))
                .collect(Collectors.toList());

        tb603.addList(tb603_1s);

        return tb603Repository.save(tb603).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb603Form dto) {
        Tb603 tb603 = tb603Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        List<Tb603_1> tb603_1s = dto.getTb603_1Dtos().stream()
                .filter(f -> StringUtils.hasText(f.getGoodsCd()))
                .map(m -> Tb603_1Dto.toTb603_1(m, tb603))
                .collect(Collectors.toList());

        tb603.updateData(dto, tb603_1s);
        return tb603.getSeq();
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb603Repository.deleteById(seq);
        return seq;
    }

    @Override
    public List<List<String>> findAllByExcel(Search060103 search) {
        // 날짜 부호 빼주기
        Search060103 realSearch = new Search060103(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), search.getStatus());

        List<Tb603> tb603s = tb603Repository.findAllByExcel(realSearch);

        // 데이터 변환에 필요한 map
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> prcsmTdMap = codeToName.commonCode("16");
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> goodsMap = codeToName.goods();

        List<List<String>> excelList = new ArrayList<>();
        int tQty = 0;
        for (Tb603 tb603 : tb603s) {
            List<Tb603_1> tb603_1s = tb603.getTb603_1s();

            // seq순 정렬
            tb603_1s.sort(Comparator.comparing(Tb603_1::getSeq));

            // 대표품목명
            String showNm = goodsMap.get(tb603_1s.get(0).getGoodsCd());
            int cnt = 0;
            int qty = 0;

            // 목표수량, 생산수량
            for (Tb603_1 tb603_1 : tb603_1s) {
                qty += tb603_1.getQty();
                cnt++;
            }
            if (cnt > 1) showNm += "외" + (cnt - 1) + "건";

            List<String> list = new ArrayList<>();
            list.add(GlobalMethod.makeYmd(tb603.getWHouseDt(),"yyyy-MM-dd") + "-" + tb603.getWHouseNo());
            list.add(wHouseMap.get(tb603.getWHouseOut()));
            list.add(showNm);
            list.add(String.valueOf(qty));
            list.add(prcsmTdMap.get(tb603.getPrcsmTd()));
            list.add(statusMap.get(tb603.getStatus()));
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
        list.add("");
        excelList.add(list);

        return excelList;
    }
}
