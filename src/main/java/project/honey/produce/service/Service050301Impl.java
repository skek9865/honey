package project.honey.produce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.Tb504Dto;
import project.honey.produce.dto.Tb504_1Dto;
import project.honey.produce.dto.Tb505Dto;
import project.honey.produce.dto.Tb505_1Dto;
import project.honey.produce.dto.form.Tb504Form;
import project.honey.produce.dto.form.Tb505Form;
import project.honey.produce.dto.search.Search050201;
import project.honey.produce.dto.search.Search050301;
import project.honey.produce.entity.Tb504;
import project.honey.produce.entity.Tb504_1;
import project.honey.produce.entity.Tb505;
import project.honey.produce.entity.Tb505_1;
import project.honey.produce.repository.Tb505Repository;
import project.honey.produce.repository.Tb505_1Repository;

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
public class Service050301Impl implements Service050301{

    private final Tb505Repository tb505Repository;
    private final Tb505_1Repository tb505_1Repository;
    private final CodeToName codeToName;

    @Override
    @Transactional
    public Integer insert(Tb505Form dto) {
        Tb505 tb505 = Tb505Dto.toTb505(dto);
        List<Tb505_1> tb505_1s = dto.getTb505_1Dtos().stream()
                .filter(f -> StringUtils.hasText(f.getGoodsCd()))
                .map(m -> Tb505_1Dto.toTb505_1(m, tb505))
                .collect(Collectors.toList());

        tb505.addList(tb505_1s);

        return tb505Repository.save(tb505).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb505Form dto) {
        Tb505 tb505 = tb505Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        List<Tb505_1> tb505_1s = dto.getTb505_1Dtos().stream()
                .filter(f -> StringUtils.hasText(f.getGoodsCd()))
                .map(m -> Tb505_1Dto.toTb505_1(m, tb505))
                .collect(Collectors.toList());

        tb505.updateData(dto, tb505_1s);
        return tb505.getSeq();
    }

    @Override
    public Page<Tb505Dto> findAll(Search050301 search, Pageable pageable) {

        // 날짜 부호 빼주기
        Search050301 realSearch = new Search050301(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), search.getStatus());

        Page<Tb505> pagingTb505s = tb505Repository.findAllByDsl(realSearch, pageable);
        List<Tb505> tb505s = pagingTb505s.getContent();

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> goodsMap = codeToName.goods();

        List<Tb505Dto> dtoList = new ArrayList<>();
        // 화면에 출력할 데이터 생성
        for (Tb505 tb505 : tb505s) {
            List<Tb505_1> tb505_1s = tb505.getTb505_1s();

            // 정렬
            tb505_1s.sort(Comparator.comparing(Tb505_1::getSeq));

            // 대표품목명
            String goodsNm = goodsMap.get(tb505_1s.get(0).getGoodsCd());
            int cnt = 0;
            int qty = 0;

            for (Tb505_1 tb505_1 : tb505_1s) {
                qty += tb505_1.getQty();
                cnt++;
            }
            if (cnt > 1) goodsNm += "외" + (cnt - 1) + "건";
            Tb505Dto dto = Tb505Dto.of(tb505.getSeq(), tb505, goodsNm, qty, wHouseMap, statusMap);
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pagingTb505s.getTotalElements());
    }

    @Override
    public Tb505Form findById(Integer seq) {

        if(seq == null){
            String wHouseDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int wHouseNo = tb505Repository.countByWHouseDt(GlobalMethod.replaceYmd(wHouseDt, "-"));
            List<Tb505_1Dto> dtoList = new ArrayList<>();
            while (dtoList.size() < 5) {
                dtoList.add(null);
            }
            return Tb505Form.builder()
                    .wHouseDt(wHouseDt)
                    .wHouseNo(wHouseNo + 1)
                    .tb505_1Dtos(dtoList)
                    .build();
        }

        Tb505 tb505 = tb505Repository.findById(seq).orElseThrow(RuntimeException::new);

        List<Tb505_1Dto> dtoList = tb505_1Repository.findAllByDtos(tb505.getSeq());
        while (dtoList.size() < 5) {
            dtoList.add(null);
        }

        return Tb505Form.of(tb505, dtoList);
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb505Repository.deleteById(seq);
        return seq;
    }

    @Override
    public List<List<String>> findAllByExcel(Search050301 search) {
        // 날짜 부호 빼주기
        Search050301 realSearch = new Search050301(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), search.getStatus());

        List<Tb505> tb505s = tb505Repository.findAllByExcel(realSearch);

        // 데이터 변환에 필요한 map
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> goodsMap = codeToName.goods();

        List<List<String>> excelList = new ArrayList<>();
        int tQty = 0;
        for (Tb505 tb505 : tb505s) {
            List<Tb505_1> tb505_1s = tb505.getTb505_1s();

            // seq순 정렬
            tb505_1s.sort(Comparator.comparing(Tb505_1::getSeq));

            // 대표품목명
            String showNm = goodsMap.get(tb505_1s.get(0).getGoodsCd());
            int cnt = 0;
            int qty = 0;

            // 목표수량, 생산수량
            for (Tb505_1 tb505_1 : tb505_1s) {
                qty += tb505_1.getQty();
                cnt++;
            }
            if (cnt > 1) showNm += "외" + (cnt - 1) + "건";

            List<String> list = new ArrayList<>();
            list.add(tb505.getWHouseDt() + "-" + tb505.getWHouseNo());
            list.add(wHouseMap.get(tb505.getWHouseIn()));
            list.add(wHouseMap.get(tb505.getWHouseOut()));
            list.add(showNm);
            list.add(String.valueOf(qty));
            list.add(statusMap.get(tb505.getStatus()));
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
