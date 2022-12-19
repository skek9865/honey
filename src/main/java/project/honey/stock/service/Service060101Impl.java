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
import project.honey.produce.dto.Tb505Dto;
import project.honey.produce.dto.Tb505_1Dto;
import project.honey.produce.dto.form.Tb505Form;
import project.honey.produce.dto.search.Search050301;
import project.honey.produce.entity.Tb505;
import project.honey.produce.entity.Tb505_1;
import project.honey.stock.dto.Tb601Dto;
import project.honey.stock.dto.Tb601_1Dto;
import project.honey.stock.dto.form.Tb601Form;
import project.honey.stock.dto.search.Search060101;
import project.honey.stock.entity.Tb601;
import project.honey.stock.entity.Tb601_1;
import project.honey.stock.repository.Tb601Repository;
import project.honey.stock.repository.Tb601_1Repository;

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
public class Service060101Impl implements Service060101{

    private final Tb601Repository tb601Repository;
    private final Tb601_1Repository tb601_1Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Tb601Dto> findAll(Search060101 search, Pageable pageable) {

        // 날짜 부호 빼주기
        Search060101 realSearch = new Search060101(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), search.getStatus());

        Page<Tb601> pagingTb601s = tb601Repository.findAllByDsl(realSearch, pageable);
        List<Tb601> tb601s = pagingTb601s.getContent();

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> goodsMap = codeToName.goods();

        List<Tb601Dto> dtoList = new ArrayList<>();
        // 화면에 출력할 데이터 생성
        for (Tb601 tb601 : tb601s) {
            List<Tb601_1> tb601_1s = tb601.getTb601_1s();

            // 정렬
            tb601_1s.sort(Comparator.comparing(Tb601_1::getSeq));

            // 대표품목명
            String goodsNm = goodsMap.get(tb601_1s.get(0).getGoodsCd());
            int cnt = 0;
            int qty = 0;

            for (Tb601_1 tb601_1 : tb601_1s) {
                qty += tb601_1.getQty();
                cnt++;
            }
            if (cnt > 1) goodsNm += "외" + (cnt - 1) + "건";
            Tb601Dto dto = Tb601Dto.of(tb601.getSeq(), tb601, goodsNm, qty, wHouseMap, statusMap);
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pagingTb601s.getTotalElements());
    }

    @Override
    public Tb601Form findById(Integer seq) {
        if(seq == null){
            String wHouseDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int wHouseNo = tb601Repository.countByWHouseDt(GlobalMethod.replaceYmd(wHouseDt, "-"));
            List<Tb601_1Dto> dtoList = new ArrayList<>();
            while (dtoList.size() < 5) {
                dtoList.add(null);
            }
            return Tb601Form.builder()
                    .wHouseDt(wHouseDt)
                    .wHouseNo(wHouseNo + 1)
                    .tb601_1Dtos(dtoList)
                    .build();
        }

        Tb601 tb601 = tb601Repository.findById(seq).orElseThrow(RuntimeException::new);

        List<Tb601_1Dto> dtoList = tb601_1Repository.findAllByDtos(tb601.getSeq());
        while (dtoList.size() < 5) {
            dtoList.add(null);
        }

        return Tb601Form.of(tb601, dtoList);
    }

    @Override
    @Transactional
    public Integer insert(Tb601Form dto) {
        Tb601 tb601 = Tb601Dto.toTb601(dto);
        List<Tb601_1> tb601_1s = dto.getTb601_1Dtos().stream()
                .filter(f -> StringUtils.hasText(f.getGoodsCd()))
                .map(m -> Tb601_1Dto.toTb601_1(m, tb601))
                .collect(Collectors.toList());

        tb601.addList(tb601_1s);

        return tb601Repository.save(tb601).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb601Form dto) {
        Tb601 tb601 = tb601Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        List<Tb601_1> tb601_1s = dto.getTb601_1Dtos().stream()
                .filter(f -> StringUtils.hasText(f.getGoodsCd()))
                .map(m -> Tb601_1Dto.toTb601_1(m, tb601))
                .collect(Collectors.toList());

        tb601.updateData(dto, tb601_1s);
        return tb601.getSeq();
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb601Repository.deleteById(seq);
        return seq;
    }

    @Override
    public List<List<String>> findAllByExcel(Search060101 search) {
        // 날짜 부호 빼주기
        Search060101 realSearch = new Search060101(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), search.getStatus());

        List<Tb601> tb601s = tb601Repository.findAllByExcel(realSearch);

        // 데이터 변환에 필요한 map
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> goodsMap = codeToName.goods();

        List<List<String>> excelList = new ArrayList<>();
        int tQty = 0;
        for (Tb601 tb601 : tb601s) {
            List<Tb601_1> tb601_1s = tb601.getTb601_1s();

            // seq순 정렬
            tb601_1s.sort(Comparator.comparing(Tb601_1::getSeq));

            // 대표품목명
            String showNm = goodsMap.get(tb601_1s.get(0).getGoodsCd());
            int cnt = 0;
            int qty = 0;

            // 목표수량, 생산수량
            for (Tb601_1 tb601_1 : tb601_1s) {
                qty += tb601_1.getQty();
                cnt++;
            }
            if (cnt > 1) showNm += "외" + (cnt - 1) + "건";

            List<String> list = new ArrayList<>();
            list.add(tb601.getWHouseDt() + "-" + tb601.getWHouseNo());
            list.add(wHouseMap.get(tb601.getWHouseIn()));
            list.add(wHouseMap.get(tb601.getWHouseOut()));
            list.add(showNm);
            list.add(String.valueOf(qty));
            list.add(statusMap.get(tb601.getStatus()));
            excelList.add(list);
            tQty += qty;
        }

        // 총계
        List<String> list = new ArrayList<>();
        list.add("");
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
