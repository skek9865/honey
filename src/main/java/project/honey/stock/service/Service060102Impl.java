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
import project.honey.stock.dto.Tb602Dto;
import project.honey.stock.dto.Tb602_1Dto;
import project.honey.stock.dto.form.Tb601Form;
import project.honey.stock.dto.form.Tb602Form;
import project.honey.stock.dto.search.Search060101;
import project.honey.stock.dto.search.Search060102;
import project.honey.stock.entity.Tb601;
import project.honey.stock.entity.Tb601_1;
import project.honey.stock.entity.Tb602;
import project.honey.stock.entity.Tb602_1;
import project.honey.stock.repository.Tb602Repository;
import project.honey.stock.repository.Tb602_1Repository;

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
public class Service060102Impl implements Service060102{

    private final Tb602Repository tb602Repository;
    private final Tb602_1Repository tb602_1Repository;
    private final CodeToName codeToName;


    @Override
    public Page<Tb602Dto> findAll(Search060102 search, Pageable pageable) {
        // 날짜 부호 빼주기
        Search060102 realSearch = new Search060102(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), search.getStatus());

        Page<Tb602> pagingTb602s = tb602Repository.findAllByDsl(realSearch, pageable);
        List<Tb602> tb602s = pagingTb602s.getContent();

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> empMap = codeToName.emp();

        List<Tb602Dto> dtoList = new ArrayList<>();
        // 화면에 출력할 데이터 생성
        for (Tb602 tb602 : tb602s) {
            List<Tb602_1> tb602_1s = tb602.getTb602_1s();

            // 정렬
            tb602_1s.sort(Comparator.comparing(Tb602_1::getSeq));

            // 대표품목명
            String goodsNm = goodsMap.get(tb602_1s.get(0).getGoodsCd());
            int cnt = 0;
            int qty = 0;

            for (Tb602_1 tb602_1 : tb602_1s) {
                qty += tb602_1.getQty();
                cnt++;
            }
            if (cnt > 1) goodsNm += "외" + (cnt - 1) + "건";
            Tb602Dto dto = Tb602Dto.of(tb602.getSeq(), tb602, goodsNm, qty, wHouseMap, statusMap,custMap, empMap);
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pagingTb602s.getTotalElements());
    }

    @Override
    public Tb602Form findById(Integer seq) {
        Map<String, String> custMap = codeToName.cust();
        if(seq == null){
            String wHouseDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int wHouseNo = tb602Repository.countByWHouseDt(GlobalMethod.replaceYmd(wHouseDt, "-"));
            List<Tb602_1Dto> dtoList = new ArrayList<>();
            while (dtoList.size() < 5) {
                dtoList.add(null);
            }
            return Tb602Form.builder()
                    .wHouseDt(wHouseDt)
                    .wHouseNo(wHouseNo + 1)
                    .tb602_1Dtos(dtoList)
                    .build();
        }

        Tb602 tb602 = tb602Repository.findById(seq).orElseThrow(RuntimeException::new);

        List<Tb602_1Dto> dtoList = tb602_1Repository.findAllByDtos(tb602.getSeq());
        while (dtoList.size() < 5) {
            dtoList.add(null);
        }

        return Tb602Form.of(tb602, dtoList, custMap);
    }

    @Override
    @Transactional
    public Integer insert(Tb602Form dto) {
        Tb602 tb602 = Tb602Dto.toTb602(dto);
        List<Tb602_1> tb602_1s = dto.getTb602_1Dtos().stream()
                .filter(f -> StringUtils.hasText(f.getGoodsCd()))
                .map(m -> Tb602_1Dto.toTb602_1(m, tb602))
                .collect(Collectors.toList());

        tb602.addList(tb602_1s);

        return tb602Repository.save(tb602).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb602Form dto) {
        Tb602 tb602 = tb602Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        List<Tb602_1> tb602_1s = dto.getTb602_1Dtos().stream()
                .filter(f -> StringUtils.hasText(f.getGoodsCd()))
                .map(m -> Tb602_1Dto.toTb602_1(m, tb602))
                .collect(Collectors.toList());

        tb602.updateData(dto, tb602_1s);
        return tb602.getSeq();
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb602Repository.deleteById(seq);
        return seq;
    }

    @Override
    public List<List<String>> findAllByExcel(Search060102 search) {
        // 날짜 부호 빼주기
        Search060102 realSearch = new Search060102(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), search.getStatus());

        List<Tb602> tb602s = tb602Repository.findAllByExcel(realSearch);

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> empMap = codeToName.emp();

        List<List<String>> excelList = new ArrayList<>();
        int tQty = 0;
        for (Tb602 tb602 : tb602s) {
            List<Tb602_1> tb602_1s = tb602.getTb602_1s();

            // seq순 정렬
            tb602_1s.sort(Comparator.comparing(Tb602_1::getSeq));

            // 대표품목명
            String showNm = goodsMap.get(tb602_1s.get(0).getGoodsCd());
            int cnt = 0;
            int qty = 0;

            // 목표수량, 생산수량
            for (Tb602_1 tb602_1 : tb602_1s) {
                qty += tb602_1.getQty();
                cnt++;
            }
            if (cnt > 1) showNm += "외" + (cnt - 1) + "건";

            List<String> list = new ArrayList<>();
            list.add(tb602.getWHouseDt() + "-" + tb602.getWHouseNo());
            list.add(custMap.get(tb602.getCustCd()));
            list.add(wHouseMap.get(tb602.getWHouseOut()));
            list.add(showNm);
            list.add(String.valueOf(qty));
            list.add(empMap.get(tb602.getEmpNo()));
            list.add(tb602.getNote());
            list.add(statusMap.get(tb602.getStatus()));
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
        list.add("");
        excelList.add(list);

        return excelList;
    }
}
