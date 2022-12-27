package project.honey.business.service.analyze;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.honey.business.dto.analaze.Dto040305;
import project.honey.business.entity.manage.Tb414;
import project.honey.business.entity.manage.Tb414_1;
import project.honey.business.form.analyze.Search040304;
import project.honey.business.repository.manage.Tb414Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Service040305Impl implements Service040305{

    private final Tb414Repository tb414Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto040305> findAllByDsl(Search040304 search040304, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040304.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040304.getSYmd2(), "-");

        Page<Tb414> result = tb414Repository.findAllBy040305(ymd1, ymd2, search040304, pageable);

        List<Dto040305> resultList = new ArrayList<>();

        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> whouseMap = codeToName.wHouse();

        for(Tb414 entity : result.getContent()){
            List<Tb414_1> tb414_1s = entity.getTb414_1s();

            String custNm = custMap.get(entity.getCustCd());
            String whouseNm = whouseMap.get(entity.getWhouseCd());

            for(Tb414_1 tb414_1 : tb414_1s){
                String goodsNm = goodsMap.get(tb414_1.getGoodsCd());
                Dto040305 dto040305 = Dto040305.of(entity, tb414_1, goodsNm, whouseNm, custNm);
                resultList.add(dto040305);
            }
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040304 search040304) {
        String ymd1 = GlobalMethod.replaceYmd(search040304.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040304.getSYmd2(), "-");

        int seq = 0;

        List<Tb414> result = tb414Repository.findAllBy040305Excel(ymd1, ymd2, search040304);

        List<List<String>> resultList = new ArrayList<>();

        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> whouseMap = codeToName.wHouse();

        for(Tb414 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb414_1> tb414_1s = entity.getTb414_1s();

            String shipNo = entity.getShipDt().substring(0,4) + "-" + entity.getShipDt().substring(4,6) + "-" + entity.getShipDt().substring(6,8) + "-" + entity.getShipNo();

            String custNm = custMap.get(entity.getCustCd());
            String whouseNm = whouseMap.get(entity.getWhouseCd());

            for(Tb414_1 tb414_1 : tb414_1s){
                String goodsNm = goodsMap.get(tb414_1.getGoodsCd());
                List<String> list = new ArrayList<>();
                list.add(shipNo);
                list.add(goodsNm);
                list.add(tb414_1.getStandard());
                list.add(String.valueOf(tb414_1.getQty()));
                list.add(whouseNm);
                list.add(custNm);
                list.add(entity.getPhone());
                list.add(tb414_1.getNote());

                resultList.add(list);
            }
            seq = entity.getSeq();
        }

        return resultList;
    }
}
