package project.honey.produce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.entity.basic.Tb405;
import project.honey.business.repository.basic.Tb405Repository;
import project.honey.produce.dto.Dto050304;
import project.honey.produce.entity.Tb503;
import project.honey.produce.entity.Tb503_1;
import project.honey.produce.repository.Tb503_1Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service050304Impl implements Service050304{

    private final Tb405Repository tb405Repository;
    private final Tb503_1Repository tb503_1Repository;

    @Override
    public Page<Dto050304> findAll(Pageable pageable) {
        Page<Tb503_1> pagingTb503_1s = tb503_1Repository.findAllByDsl(pageable);
        List<Tb503_1> tb503_1s = pagingTb503_1s.getContent();

        List<Dto050304> dtoList = new ArrayList<>();

        // dtoList 생성
        for (Tb503_1 tb503_1 : tb503_1s) {
            Tb503 tb503 = tb503_1.getTb503();
            Tb405 tb405By503 = tb405Repository.findByGoodsCd(tb503.getGoodsCd()).orElseThrow(RuntimeException::new);
            Tb405 tb405By503_1 = tb405Repository.findByGoodsCd(tb503_1.getGoodsCd()).orElseThrow(RuntimeException::new);

            Dto050304 dto = Dto050304.of(tb503, tb405By503, tb503_1, tb405By503_1);
            dtoList.add(dto);
        }

        // 리스트 정렬
        dtoList.sort(Comparator.comparing(Dto050304::getSeq));

        // 겹치는 Tb503 값 비워주기
        int seq = -1;
        List<Dto050304> result = new ArrayList<>();
        for (Dto050304 dto : dtoList) {
            if (dto.getSeq() != seq) {
                seq = dto.getSeq();
            } else{
                dto.setPGoodsNm("");
            }
            result.add(dto);
        }

        return new PageImpl<>(result, pageable, pagingTb503_1s.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel() {

        List<Tb503_1> tb503_1s = tb503_1Repository.findAllByExcel();

        List<Dto050304> dtoList = new ArrayList<>();
        for (Tb503_1 tb503_1 : tb503_1s) {
            Tb503 tb503 = tb503_1.getTb503();
            Tb405 tb405By503 = tb405Repository.findByGoodsCd(tb503.getGoodsCd()).orElseThrow(RuntimeException::new);
            Tb405 tb405By503_1 = tb405Repository.findByGoodsCd(tb503_1.getGoodsCd()).orElseThrow(RuntimeException::new);

            Dto050304 dto = Dto050304.of(tb503, tb405By503, tb503_1, tb405By503_1);
            dtoList.add(dto);
        }

        // 리스트 정렬
        dtoList.sort(Comparator.comparing(Dto050304::getSeq));

        // 겹치는 Tb503 값 비워주기
        int seq = -1;
        List<Dto050304> result = new ArrayList<>();
        for (Dto050304 dto : dtoList) {
            if (dto.getSeq() != seq) {
                seq = dto.getSeq();
            } else{
                dto.setPGoodsNm("");
            }
            result.add(dto);
        }

        //엑셀 변환
        List<List<String>> excelList = new ArrayList<>();
        int tPQty = 0;
        int tSQty = 0;
        int tCQty = 0;
        int tCPrice = 0;
        int tDPrice = 0;

        for (Dto050304 dto : result) {
            int pQty = dto.getPQty();
            int sQty = dto.getSQty();
            int cQty = dto.getCQty();
            int cPrice = dto.getCPrice();
            int dPrice = dto.getDPrice();

            List<String> list = new ArrayList<>();
            list.add(dto.getPGoodsNm());
            list.add(dto.getCGoodsNm());
            list.add(String.valueOf(pQty));
            list.add(String.valueOf(sQty));
            list.add(String.valueOf(cQty));
            list.add(String.valueOf(cPrice));
            list.add(String.valueOf(dPrice));

            excelList.add(list);

            tPQty += pQty;
            tSQty += sQty;
            tCQty += cQty;
            tCPrice += cPrice;
            tDPrice += dPrice;
        }
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("총계");
        list.add(String.valueOf(tPQty));
        list.add(String.valueOf(tSQty));
        list.add(String.valueOf(tCQty));
        list.add(String.valueOf(tCPrice));
        list.add(String.valueOf(tDPrice));
        excelList.add(list);

        return excelList;
    }
}
