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
import project.honey.produce.dto.Tb505Dto;
import project.honey.produce.dto.Tb505_1Dto;
import project.honey.produce.dto.Tb506Dto;
import project.honey.produce.dto.Tb506_1Dto;
import project.honey.produce.dto.form.Tb505Form;
import project.honey.produce.dto.form.Tb506Form;
import project.honey.produce.dto.search.Search050301;
import project.honey.produce.dto.search.Search050302;
import project.honey.produce.entity.Tb505;
import project.honey.produce.entity.Tb505_1;
import project.honey.produce.entity.Tb506;
import project.honey.produce.entity.Tb506_1;
import project.honey.produce.repository.Tb505Repository;
import project.honey.produce.repository.Tb505_1Repository;
import project.honey.produce.repository.Tb506Repository;
import project.honey.produce.repository.Tb506_1Repository;

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
public class Service050302Impl implements Service050302{

    private final Tb506Repository tb506Repository;
    private final Tb506_1Repository tb506_1Repository;
    private final CodeToName codeToName;

    @Override
    @Transactional
    public Integer insert(Tb506Form dto) {
        Tb506 tb506 = Tb506Dto.toTb506(dto);
        List<Tb506_1> tb506_1s = dto.getTb506_1Dtos().stream()
                .filter(f -> StringUtils.hasText(f.getGoodsCd()))
                .map(m -> Tb506_1Dto.toTb506_1(m, tb506))
                .collect(Collectors.toList());

        tb506.addList(tb506_1s);

        return tb506Repository.save(tb506).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb506Form dto) {
        Tb506 tb506 = tb506Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        List<Tb506_1> tb506_1s = dto.getTb506_1Dtos().stream()
                .filter(f -> StringUtils.hasText(f.getGoodsCd()))
                .map(m -> Tb506_1Dto.toTb506_1(m, tb506))
                .collect(Collectors.toList());

        tb506.updateData(dto, tb506_1s);
        return tb506.getSeq();
    }

    @Override
    public Page<Tb506Dto> findAll(Search050302 search, Pageable pageable) {

        // 날짜 부호 빼주기
        Search050302 realSearch = new Search050302(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), search.getStatus());

        Page<Tb506> pagingTb506s = tb506Repository.findAllByDsl(realSearch, pageable);
        List<Tb506> tb506s = pagingTb506s.getContent();

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> goodsMap = codeToName.goods();

        List<Tb506Dto> dtoList = new ArrayList<>();
        // 화면에 출력할 데이터 생성
        for (Tb506 tb506 : tb506s) {
            List<Tb506_1> tb506_1s = tb506.getTb506_1s();

            // 정렬
            tb506_1s.sort(Comparator.comparing(Tb506_1::getSeq));

            // 대표품목명
            String goodsNm = goodsMap.get(tb506_1s.get(0).getGoodsCd());
            int cnt = 0;
            int qty = 0;

            for (Tb506_1 tb506_1 : tb506_1s) {
                qty += tb506_1.getQty();
                cnt++;
            }
            if (cnt > 1) goodsNm += "외" + (cnt - 1) + "건";
            Tb506Dto dto = Tb506Dto.of(tb506.getSeq(), tb506, goodsNm, qty, wHouseMap, statusMap);
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pagingTb506s.getTotalElements());
    }

    @Override
    public Tb506Form findById(Integer seq) {

        if(seq == null){
            String wHouseDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int wHouseNo = tb506Repository.countByWHouseDt(GlobalMethod.replaceYmd(wHouseDt, "-"));
            List<Tb506_1Dto> dtoList = new ArrayList<>();
            while (dtoList.size() < 5) {
                dtoList.add(null);
            }
            return Tb506Form.builder()
                    .wHouseDt(wHouseDt)
                    .wHouseNo(wHouseNo + 1)
                    .tb506_1Dtos(dtoList)
                    .build();
        }

        Tb506 tb506 = tb506Repository.findById(seq).orElseThrow(RuntimeException::new);

        List<Tb506_1Dto> dtoList = tb506_1Repository.findAllByDtos(tb506.getSeq());
        while (dtoList.size() < 5) {
            dtoList.add(null);
        }

        return Tb506Form.of(tb506, dtoList);
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb506Repository.deleteById(seq);
        return seq;
    }

    @Override
    public List<List<String>> findAllByExcel(Search050302 search) {
        // 날짜 부호 빼주기
        Search050302 realSearch = new Search050302(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), search.getStatus());

        List<Tb506> tb506s = tb506Repository.findAllByExcel(realSearch);

        // 데이터 변환에 필요한 map
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> goodsMap = codeToName.goods();

        List<List<String>> excelList = new ArrayList<>();
        int tQty = 0;
        for (Tb506 tb506 : tb506s) {
            List<Tb506_1> tb506_1s = tb506.getTb506_1s();

            // seq순 정렬
            tb506_1s.sort(Comparator.comparing(Tb506_1::getSeq));

            // 대표품목명
            String showNm = goodsMap.get(tb506_1s.get(0).getGoodsCd());
            int cnt = 0;
            int qty = 0;

            // 목표수량, 생산수량
            for (Tb506_1 tb506_1 : tb506_1s) {
                qty += tb506_1.getQty();
                cnt++;
            }
            if (cnt > 1) showNm += "외" + (cnt - 1) + "건";

            List<String> list = new ArrayList<>();
            list.add(tb506.getWHouseDt() + "-" + tb506.getWHouseNo());
            list.add(wHouseMap.get(tb506.getWHouseIn()));
            list.add(wHouseMap.get(tb506.getWHouseOut()));
            list.add(showNm);
            list.add(String.valueOf(qty));
            list.add(statusMap.get(tb506.getStatus()));
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
