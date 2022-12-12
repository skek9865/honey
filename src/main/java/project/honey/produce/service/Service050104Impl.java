package project.honey.produce.service;

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
import project.honey.produce.dto.Dto050104;
import project.honey.produce.entity.Tb503;
import project.honey.produce.entity.Tb503_1;
import project.honey.produce.repository.Tb503_1Repository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service050104Impl implements Service050104{

    private final Tb405Repository tb405Repository;
    private final Tb503_1Repository tb503_1Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto050104> findAll(String goodsCd, Pageable pageable) {
        // 필요한 데이터 세팅
        Page<Tb503_1> pagingTb503_1s = tb503_1Repository.findAll(pageable);
        List<Tb503_1> tb503_1s = pagingTb503_1s.getContent();
        Map<String, String> classMap = codeToName.commonCode("07");

        List<Dto050104> dtoList = new ArrayList<>();

        // dtoList 생성
        for (Tb503_1 tb503_1 : tb503_1s) {
            Tb503 tb503 = tb503_1.getTb503();
            Tb405 tb405By503 = tb405Repository.findByGoodsCd(tb503.getGoodsCd()).orElseThrow(RuntimeException::new);
            Tb405 tb405By503_1 = tb405Repository.findByGoodsCd(tb503_1.getGoodsCd()).orElseThrow(RuntimeException::new);
            Dto050104 dto = Dto050104.of(tb503, tb405By503, tb503_1, tb405By503_1, classMap);
            dtoList.add(dto);
        }
        // 검색 필터
        if(StringUtils.hasText(goodsCd))
            dtoList = dtoList.stream().filter(f -> f.getGoodsCd().equals(goodsCd)).collect(Collectors.toList());

        // 리스트 정렬
        dtoList.sort(Comparator.comparing(Dto050104::getSeq));

        // 겹치는 Tb503 값 비워주기
        int seq = -1;
        List<Dto050104> result = new ArrayList<>();
        for (Dto050104 dto : dtoList) {
            if (dto.getSeq() != seq) {
                seq = dto.getSeq();
            } else{
                dto = Dto050104.builder()
                        .seq(dto.getSeq())
                        .fClassSeq(dto.getFClassSeq())
                        .fGoodsCd(dto.getFGoodsCd())
                        .fGoodsNm(dto.getFGoodsNm())
                        .fStandard(dto.getFStandard())
                        .fQty(dto.getFQty())
                        .build();
            }
            result.add(dto);
        }

        return new PageImpl<>(result, pageable, pagingTb503_1s.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(String goodsCd) {
        // 필요한 데이터 세팅
        List<Tb503_1> tb503_1s = tb503_1Repository.findAll();
        Map<String, String> classMap = codeToName.commonCode("07");

        List<Dto050104> dtoList = new ArrayList<>();

        // dtoList 생성
        for (Tb503_1 tb503_1 : tb503_1s) {
            Tb503 tb503 = tb503_1.getTb503();
            Tb405 tb405By503 = tb405Repository.findByGoodsCd(tb503.getGoodsCd()).orElseThrow(RuntimeException::new);
            Tb405 tb405By503_1 = tb405Repository.findByGoodsCd(tb503_1.getGoodsCd()).orElseThrow(RuntimeException::new);
            Dto050104 dto = Dto050104.of(tb503, tb405By503, tb503_1, tb405By503_1, classMap);
            dtoList.add(dto);
        }
        // 검색 필터
        if(StringUtils.hasText(goodsCd))
            dtoList = dtoList.stream().filter(f -> f.getGoodsCd().equals(goodsCd)).collect(Collectors.toList());

        // 리스트 정렬
        dtoList.sort(Comparator.comparing(Dto050104::getSeq));

        // 겹치는 Tb503 값 비워주기
        int seq = -1;
        List<Dto050104> result = new ArrayList<>();
        for (Dto050104 dto : dtoList) {
            if (dto.getSeq() != seq) {
                seq = dto.getSeq();
            } else{
                dto = Dto050104.builder()
                        .seq(dto.getSeq())
                        .fClassSeq(dto.getFClassSeq())
                        .fGoodsCd(dto.getFGoodsCd())
                        .fGoodsNm(dto.getFGoodsNm())
                        .fStandard(dto.getFStandard())
                        .fQty(dto.getFQty())
                        .build();
            }
            result.add(dto);
        }

        // 엑셀 변환
        List<List<String>> excelList = new ArrayList<>();
        int totalQty = 0;
        int totalFQty = 0;
        for (Dto050104 dto : result) {
            List<String> list = new ArrayList<>();
            list.add(dto.getClassSeq());
            list.add(dto.getGoodsCd());
            list.add(dto.getGoodsNm());
            list.add(dto.getStandard());
            list.add(dto.getQty() != null ? String.valueOf(dto.getQty()) : "");
            list.add(dto.getFClassSeq());
            list.add(dto.getFGoodsCd());
            list.add(dto.getFGoodsNm());
            list.add(dto.getFStandard());
            list.add(String.valueOf(dto.getFQty()));
            excelList.add(list);
            totalQty += dto.getQty() != null ? dto.getQty() : 0;
            totalFQty += dto.getFQty() != null ? dto.getFQty() : 0;
        }
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("총계");
        list.add(String.valueOf(totalQty));
        list.add("");
        list.add("");
        list.add("");
        list.add("총계");
        list.add(String.valueOf(totalFQty));
        excelList.add(list);

        return excelList;
    }
}
