package project.honey.produce.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import project.honey.business.dto.search.Search405;
import project.honey.business.entity.basic.Tb405;
import project.honey.business.repository.basic.Tb405Repository;
import project.honey.comm.CodeToName;
import project.honey.produce.dto.Tb503Dto;
import project.honey.produce.dto.Tb503_1Dto;
import project.honey.produce.dto.form.Tb503Form;
import project.honey.produce.entity.Tb503;
import project.honey.produce.entity.Tb503_1;
import project.honey.produce.repository.Tb503Repository;
import project.honey.produce.repository.Tb503_1Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service050103Impl implements Service050103{

    private final Tb405Repository tb405Repository;
    private final Tb503Repository tb503Repository;
    private final Tb503_1Repository tb503_1Repository;
    private final CodeToName codeToName;

    @Override
    @Transactional
    public Integer insert(Tb503Form dto) {
        Tb503 tb503 = Tb503Dto.toTb503(dto);
        List<Tb503_1> tb503_1s = dto.getTb503_1Dtos().stream()
                .filter(f -> StringUtils.hasText(f.getGoodsCd()))
                .map(m -> Tb503_1Dto.toTb503_1(m, tb503))
                .collect(Collectors.toList());

        tb503.addList(tb503_1s);

        return tb503Repository.save(tb503).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb503Form dto) {
        Tb503 tb503 = tb503Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        List<Tb503_1> tb503_1s = dto.getTb503_1Dtos().stream()
                .filter(f -> StringUtils.hasText(f.getGoodsCd()))
                .map(m -> Tb503_1Dto.toTb503_1(m, tb503))
                .collect(Collectors.toList());

        tb503.updateData(dto, tb503_1s);
        return tb503.getSeq();
    }

    @Override
    public Page<Tb503Dto> findAll(Search405 search405, Pageable pageable) {
        Page<Tb405> pagingTb405s = tb405Repository.findAllByDsl(search405, pageable);
        List<Tb405> tb405s = pagingTb405s.getContent();
        List<Tb503> tb503s = tb503Repository.findAll();

        Map<String, String> classMap = codeToName.commonCode("07");
        Map<String, String> productMap = codeToName.product();

        List<Tb503Dto> dtoList = new ArrayList<>();
        for (Tb405 tb405 : tb405s) {
            int sum = 0;
            Integer seq = null;
            for (Tb503 tb503 : tb503s) {
                if (tb405.getGoodsCd().equals(tb503.getGoodsCd())) {
                    sum = tb503.getTb503_1s().stream().mapToInt(Tb503_1::getQty).sum();
                    seq = tb503.getSeq();
                    break;
                }
            }
            Tb503Dto dto = Tb503Dto.of(tb405, seq, classMap, productMap, sum);
            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList, pageable, pagingTb405s.getTotalElements());
    }

    @Override
    public Tb503Form findById(Integer seq, String goodsCd) {
        Map<String, String> productMap = codeToName.product();

        // 등록 요청
        if (StringUtils.hasText(goodsCd)) {
            Tb405 tb405 = tb405Repository.findByGoodsCd(goodsCd).orElseThrow(RuntimeException::new);
            List<Tb503_1Dto> dtos = new ArrayList<>();
            while (dtos.size() < 5) {
                dtos.add(null);
            }
            return Tb503Form.builder()
                    .goodsCd(tb405.getGoodsCd())
                    .goodsNm(tb405.getGoodsNm())
                    .productCd(tb405.getProduct())
                    .productNm(productMap.get(tb405.getProduct()))
                    .tb503_1Dtos(dtos)
                    .build();
        }

        // 수정 요청
        Tb503 tb503 = tb503Repository.findById(seq).orElseThrow(RuntimeException::new);
        Tb405 tb405 = tb405Repository.findByGoodsCd(tb503.getGoodsCd()).orElseThrow(RuntimeException::new);
        Integer fk = tb503.getSeq();

        List<Tb503_1Dto> dtos = tb503_1Repository.findAllByDtos(fk);
        while (dtos.size() < 5) {
            dtos.add(null);
        }
        return Tb503Form.of(tb503, tb405, dtos, productMap);
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb503Repository.deleteById(seq);
        return seq;
    }

    @Override
    public List<List<String>> findAllByExcel(Search405 search) {
        Map<String, String> classMap = codeToName.commonCode("07");
        Map<String, String> productMap = codeToName.product();
        List<Tb405> tb405s = tb405Repository.findAllByExcel(search);
        List<Tb503> tb503s = tb503Repository.findAll();
        List<List<String>> excelList = new ArrayList<>();

        int total = 0;
        for (Tb405 tb405 : tb405s) {
            List<String> list = new ArrayList<>();
            int sum = 0;

            // 합계 구하기
            for (Tb503 tb503 : tb503s) {
                if (tb405.getGoodsCd().equals(tb503.getGoodsCd())) {
                    sum = tb503.getTb503_1s().stream().mapToInt(Tb503_1::getQty).sum();
                    break;
                }
            }
            list.add(tb405.getGoodsCd());
            list.add(tb405.getGoodsNm());
            list.add(classMap.get(tb405.getClassSeq()));
            list.add(productMap.get(tb405.getProduct()));
            list.add(String.valueOf(sum != 0 ? sum : ""));
            excelList.add(list);

            total += sum;
        }
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("총계");
        list.add(String.valueOf(total));
        excelList.add(list);
        return excelList;
    }
}
