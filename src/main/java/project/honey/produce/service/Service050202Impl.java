package project.honey.produce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;
import project.honey.produce.dto.Tb504Dto;
import project.honey.produce.dto.Tb504_2Dto;
import project.honey.produce.dto.form.Tb504_1Form;
import project.honey.produce.dto.search.Search050201;
import project.honey.produce.entity.Tb504;
import project.honey.produce.entity.Tb504_1;
import project.honey.produce.entity.Tb504_2;
import project.honey.produce.repository.Tb504Repository;
import project.honey.produce.repository.Tb504_1Repository;
import project.honey.produce.repository.Tb504_2Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service050202Impl implements Service050202{

    private final Tb504_1Repository tb504_1Repository;
    private final Tb504_2Repository tb504_2Repository;
    private final CodeToName codeToName;

    @Override
    @Transactional
    public Integer insert(Tb504_1Form dto) {
        String workDt = GlobalMethod.replaceYmd(dto.getWorkDt2(),"-");
        Tb504_1 tb504_1 = tb504_1Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        List<Tb504_2Dto> tb504_2Dtos = dto.getTb504_2Dtos().stream().filter(f -> f.getQty() != 0).collect(Collectors.toList());
        // 504_2의 작업지시순서
        int workDtNo = 1;
        // 504_2 엔티티 만들어서 저장
        for (Tb504_2Dto tb504_2Dto : tb504_2Dtos) {
            Tb504_2 tb504_2 = Tb504_2Dto.toTb504_2(tb504_2Dto, tb504_1, workDt, workDtNo);
            tb504_2Repository.save(tb504_2);
            workDtNo++;
        }
        return tb504_1.getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb504_1Form dto) {
        String workDt = GlobalMethod.replaceYmd(dto.getWorkDt2(),"-");
        Tb504_1 tb504_1 = tb504_1Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        List<Tb504_2Dto> tb504_2Dtos = dto.getTb504_2Dtos().stream().filter(f -> f.getQty() != 0).collect(Collectors.toList());
        // 504_2의 작업지시순서
        int workDtNo = 1;

        // 504_2 엔티티 리스트 생성
        List<Tb504_2> tb504_2s = new ArrayList<>();
        for (Tb504_2Dto tb504_2Dto : tb504_2Dtos) {
            Tb504_2 tb504_2 = Tb504_2Dto.toTb504_2(tb504_2Dto, tb504_1, workDt, workDtNo);
            tb504_2s.add(tb504_2);
            workDtNo++;
        }

        // 컬렉션 교체
        tb504_1.changeTb504_2s(tb504_2s);
        return tb504_1.getSeq();
    }

    @Override
    public Page<Tb504Dto> findAll(Search050201 search, Pageable pageable) {

        // 날짜 부호 빼주기
        Search050201 realSearch = new Search050201(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"), search.getStatus());

        Page<Tb504_1> pagingTb504_1s = tb504_1Repository.findAllByDsl(realSearch, pageable);
        Map<String, String> statusMap = codeToName.commonCode("18");
        List<Tb504_1> tb504_1s = pagingTb504_1s.getContent();

        // 데이터 변환에 필요한 map
        Map<String, String> empMap = codeToName.emp();
        Map<String, String> goodsMap = codeToName.goods();

        List<Tb504Dto> dtoList = new ArrayList<>();
        for (Tb504_1 tb504_1 : tb504_1s) {
            Tb504 tb504 = tb504_1.getTb504();
            String goodsNm = goodsMap.get(tb504_1.getGoodsCd());
            int rQty = 0;
            rQty = tb504_1.getTb504_2s().stream().mapToInt(Tb504_2::getRQty).sum();

            Tb504Dto dto = Tb504Dto.of(tb504_1.getSeq(),tb504, goodsNm, tb504_1.getQty(), rQty, empMap, statusMap);
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, pagingTb504_1s.getTotalElements());
    }

    @Override
    public Tb504_1Form findById(Integer seq) {

        Tb504_1 tb504_1 = tb504_1Repository.findById(seq).orElseThrow(RuntimeException::new);
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> goods = codeToName.goods();

        List<Tb504_2Dto> dtoList;
        if (tb504_1.getTb504_2s() == null) {
            dtoList = new ArrayList<>();
        }else{
            dtoList = tb504_1.getTb504_2s().stream()
                    .map(m -> Tb504_2Dto.of(m, tb504_1.getSeq()))
                    .collect(Collectors.toList());
        }

        // 작업지시순서순으로 정렬
        dtoList.sort(Comparator.comparing(Tb504_2Dto::getWorkDtNo));

        // 사이즈 맞춰주기
        while (dtoList.size() < 5) {
            dtoList.add(null);
        }

        return Tb504_1Form.of(tb504_1, custMap,goods, dtoList);
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        Tb504_1 tb504_1 = tb504_1Repository.findById(seq).orElseThrow(RuntimeException::new);
        tb504_1.getTb504_2s().clear();
        return seq;
    }
}
