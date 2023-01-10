package project.honey.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;
import project.honey.stock.dto.Tb604Dto;
import project.honey.stock.dto.form.Tb604Form;
import project.honey.stock.dto.search.Search060110;
import project.honey.stock.entity.Tb604;
import project.honey.stock.repository.Tb604Repository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service060110Impl implements Service060110{

    private final Tb604Repository tb604Repository;
    private final CodeToName codeToName;


    @Override
    public Page<Tb604Dto> findAll(Search060110 search, Pageable pageable) {
        // 날짜 부호 빼주기
        Search060110 realSearch = new Search060110(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"));

        Page<Tb604> pagingTb604s = tb604Repository.findAllByDsl(realSearch, pageable);

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> empMap = codeToName.emp();

        return pagingTb604s.map(m -> Tb604Dto.of(m, empMap, wHouseMap, goodsMap));
    }

    @Override
    public Tb604Form findById(Integer seq) {
        if (seq == null) {
            String wHouseDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int wHouseNo = tb604Repository.countByWHouseDt(GlobalMethod.replaceYmd(wHouseDt, "-"));
            return Tb604Form.builder()
                    .wHouseDt(wHouseDt)
                    .wHouseNo(wHouseNo + 1)
                    .build();
        }

        Map<String, String> goodsMap = codeToName.goods();

        Tb604 tb604 = tb604Repository.findById(seq).orElseThrow(EntityNotFoundException::new);

        return Tb604Form.of(tb604, goodsMap);
    }

    @Override
    @Transactional
    public Integer insert(Tb604Form dto) {
        return tb604Repository.save(Tb604Form.toTb604(dto)).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb604Form dto) {
        Tb604 tb604 = tb604Repository.findById(dto.getSeq()).orElseThrow(EntityNotFoundException::new);
        tb604.updateData(dto);
        return tb604.getSeq();
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb604Repository.deleteById(seq);
        return seq;
    }

    @Override
    public List<List<String>> findAllByExcel(Search060110 search) {

        // 날짜 부호 빼주기
        Search060110 realSearch = new Search060110(GlobalMethod.replaceYmd(search.getYmd1(), "-"),
                GlobalMethod.replaceYmd(search.getYmd2(), "-"));

        // 데이터 변환에 필요한 map
        Map<String, String> wHouseMap = codeToName.wHouse();
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> empMap = codeToName.emp();

        List<Tb604> tb604s = tb604Repository.findAllByExcel(realSearch);

        List<List<String>> excelData = new ArrayList<>();
        int stQty = 0;
        int reQty = 0;
        int adQty = 0;
        for(Tb604 tb604 : tb604s){
            List<String> list = new ArrayList<>();
            list.add(GlobalMethod.makeYmd(tb604.getWHouseDt(), "yyyy-MM-dd") + "-" + tb604.getWHouseNo());
            list.add(empMap.get(tb604.getEmpNo()));
            list.add(wHouseMap.get(tb604.getWHouseOut()));
            list.add(goodsMap.get(tb604.getGoodsCd()));
            list.add(String.valueOf(tb604.getStQty()));
            list.add(String.valueOf(tb604.getReQty()));
            list.add(String.valueOf(tb604.getAdQty()));
            list.add(tb604.getNote());
            excelData.add(list);

            stQty += tb604.getStQty();
            reQty += tb604.getReQty();
            adQty += tb604.getAdQty();
        }
        //총계
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("총계");
        list.add(String.valueOf(stQty));
        list.add(String.valueOf(reQty));
        list.add(String.valueOf(adQty));
        list.add("");
        excelData.add(list);
        return excelData;
    }
}
