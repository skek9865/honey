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
import project.honey.produce.dto.form.Tb504Form;
import project.honey.produce.dto.search.Search050201;
import project.honey.produce.entity.Tb504;
import project.honey.produce.entity.Tb504_1;
import project.honey.produce.repository.Tb504Repository;
import project.honey.produce.repository.Tb504_1Repository;

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
public class Service050201Impl implements Service050201{

    private final Tb504Repository tb504Repository;
    private final Tb504_1Repository tb504_1Repository;
    private final CodeToName codeToName;

    @Override
    @Transactional
    public Integer insert(Tb504Form dto) {
        Tb504 tb504 = Tb504Dto.toTb504(dto);
        List<Tb504_1> tb504_1s = dto.getTb504_1Dtos().stream()
                .filter(f -> StringUtils.hasText(f.getGoodsCd()))
                .map(m -> Tb504_1Dto.toTb504_1(m, tb504))
                .collect(Collectors.toList());

        tb504.addList(tb504_1s);

        return tb504Repository.save(tb504).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb504Form dto) {
        Tb504 tb504 = tb504Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        List<Tb504_1> tb504_1s = dto.getTb504_1Dtos().stream()
                .filter(f -> StringUtils.hasText(f.getGoodsCd()))
                .map(m -> Tb504_1Dto.toTb504_1(m, tb504))
                .collect(Collectors.toList());

        tb504.updateData(dto, tb504_1s);
        return tb504.getSeq();
    }

    @Override
    public Page<Tb504Dto> findAll(Search050201 search, Pageable pageable) {

        // 날짜 부호 빼주기
        Search050201 realSearch = new Search050201(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), search.getStatus());

        Page<Tb504> pagingTb504s = tb504Repository.findAllByDsl(realSearch, pageable);

        List<Tb504> tb504s = pagingTb504s.getContent();

        // 데이터 변환에 필요한 map
        Map<String, String> empMap = codeToName.emp();
        Map<String, String> statusMap = codeToName.commonCode("18");
        Map<String, String> goodsMap = codeToName.goods();

        // 화면에 출력할 데이터 생성
        List<Tb504Dto> dtoList = new ArrayList<>();
        for (Tb504 tb504 : tb504s) {
            List<Tb504_1> tb504_1s = tb504.getTb504_1s();

            // seq순 정렬
            tb504_1s.sort(Comparator.comparing(Tb504_1::getSeq));

            // 대표품목명
            String goodsNm = goodsMap.get(tb504_1s.get(0).getGoodsCd());
            int cnt = 0;
            int qty = 0;
            int rQty = 0;

            // 목표수량, 생산수량
            for (Tb504_1 tb504_1 : tb504_1s) {
                qty += tb504_1.getQty();
                rQty += tb504_1.getRQty();
                cnt++;
            }
            if (cnt > 1) goodsNm += "외" + (cnt - 1) + "건";
            Tb504Dto dto = Tb504Dto.of(tb504.getSeq(),tb504, goodsNm, qty, rQty, empMap, statusMap);
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pagingTb504s.getTotalElements());
    }

    @Override
    public Tb504Form findById(Integer seq) {
        if(seq == null){
            String workDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String deadDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int workDtNo = tb504Repository.countByWorkDt(GlobalMethod.replaceYmd(workDt, "-"));
            List<Tb504_1Dto> dtoList = new ArrayList<>();
            while (dtoList.size() < 5) {
                dtoList.add(null);
            }
            return Tb504Form.builder()
                    .workDt(workDt)
                    .deadDt(deadDt)
                    .workDtNo(workDtNo+1)
                    .tb504_1Dtos(dtoList)
                    .build();
        }

        Tb504 tb504 = tb504Repository.findById(seq).orElseThrow(RuntimeException::new);
        Map<String, String> custMap = codeToName.cust();

        List<Tb504_1Dto> dtoList = tb504_1Repository.findAllByDtos(tb504.getSeq());
        while (dtoList.size() < 5) {
            dtoList.add(null);
        }

        return Tb504Form.of(tb504, dtoList, custMap);
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb504Repository.deleteById(seq);
        return seq;
    }

    @Override
    public List<List<String>> findAllByExcel(Search050201 search) {

        // 날짜 부호 빼주기
        Search050201 realSearch = new Search050201(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), search.getStatus());

        List<Tb504> tb504s = tb504Repository.findAllByExcel(realSearch);

        // 데이터 변환에 필요한 map
        Map<String, String> empMap = codeToName.emp();
        Map<String, String> statusMap = codeToName.commonCode("18");
        Map<String, String> goodsMap = codeToName.goods();

        List<List<String>> excelList = new ArrayList<>();
        int tQty = 0;
        int tRQty = 0;
        for (Tb504 tb504 : tb504s) {
            List<Tb504_1> tb504_1s = tb504.getTb504_1s();

            // seq순 정렬
            tb504_1s.sort(Comparator.comparing(Tb504_1::getSeq));

            // 대표품목명
            String showNm = goodsMap.get(tb504_1s.get(0).getGoodsCd());
            int cnt = 0;
            int qty = 0;
            int rQty = 0;

            // 목표수량, 생산수량
            for (Tb504_1 tb504_1 : tb504_1s) {
                qty += tb504_1.getQty();
                rQty += tb504_1.getRQty();
                cnt++;
            }
            if (cnt > 1) showNm += "외" + (cnt - 1) + "건";

            List<String> list = new ArrayList<>();
            list.add(GlobalMethod.makeYmd(tb504.getWorkDt(),"yyyy-MM-dd") + "-" + tb504.getWorkDtNo());
            list.add(empMap.get(tb504.getEmpNo()));
            list.add(showNm);
            list.add(GlobalMethod.makeYmd(tb504.getDeadDt(),"yyyy-MM-dd"));
            list.add(String.valueOf(qty));
            list.add(String.valueOf(rQty));
            list.add(statusMap.get(tb504.getStatus()));
            excelList.add(list);
            tQty += qty;
            tRQty += rQty;
        }

        // 총계
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("총계");
        list.add(String.valueOf(tQty));
        list.add(String.valueOf(tRQty));
        list.add("");
        list.add("");
        excelList.add(list);

        return excelList;
    }
}
