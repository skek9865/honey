package project.honey.business.service.analyze;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.honey.business.dto.analaze.Dto040304;
import project.honey.business.entity.manage.Tb413;
import project.honey.business.entity.manage.Tb413_1;
import project.honey.business.form.analyze.Search040304;
import project.honey.business.repository.manage.Tb413Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Service040304Impl implements Service040304{

    private final Tb413Repository tb413Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto040304> findAllByDsl(Search040304 search040304, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040304.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040304.getSYmd2(), "-");

        Page<Tb413> result = tb413Repository.findAllBy040304(ymd1, ymd2, search040304, pageable);

        List<Dto040304> resultList = new ArrayList<>();

        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> whouseMap = codeToName.wHouse();

        for(Tb413 entity : result.getContent()){
            List<Tb413_1> tb413_1s = entity.getTb413_1s();

            String custNm = custMap.get(entity.getCustCd());
            String whouseNm = whouseMap.get(entity.getWhouseCd());

            for(Tb413_1 tb413_1 : tb413_1s){
                String goodsNm = goodsMap.get(tb413_1.getGoodsCd());
                Dto040304 dto040304 = Dto040304.of(entity, tb413_1, goodsNm, whouseNm, custNm);
                resultList.add(dto040304);
            }
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040304 search040304) {
        String ymd1 = GlobalMethod.replaceYmd(search040304.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040304.getSYmd2(), "-");

        int seq = 0;

        List<Tb413> result = tb413Repository.findAllBy040304Excel(ymd1, ymd2, search040304);

        List<List<String>> resultList = new ArrayList<>();

        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> whouseMap = codeToName.wHouse();

        for(Tb413 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb413_1> tb413_1s = entity.getTb413_1s();

            String shipNo = entity.getShipDt().substring(0,4) + "-" + entity.getShipDt().substring(4,6) + "-" + entity.getShipDt().substring(6,8) + "-" + entity.getShipNo();

            String custNm = custMap.get(entity.getCustCd());
            String whouseNm = whouseMap.get(entity.getWhouseCd());

            for(Tb413_1 tb413_1 : tb413_1s){
                String goodsNm = goodsMap.get(tb413_1.getGoodsCd());
                List<String> list = new ArrayList<>();
                list.add(shipNo);
                list.add(goodsNm);
                list.add(tb413_1.getStandard());
                list.add(String.valueOf(tb413_1.getQty()));
                list.add(whouseNm);
                list.add(custNm);
                list.add(entity.getPhone());
                list.add(tb413_1.getNote());

                resultList.add(list);
            }
            seq = entity.getSeq();
        }

        return resultList;
    }
}
