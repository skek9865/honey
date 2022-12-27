package project.honey.business.service.analyze;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.honey.business.dto.analaze.Dto040302;
import project.honey.business.entity.manage.Tb410;
import project.honey.business.entity.manage.Tb410_1;
import project.honey.business.form.analyze.Search040302;
import project.honey.business.repository.manage.Tb410Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.util.*;

@Service
@RequiredArgsConstructor
public class Service040302Impl implements Service040302{

    private final Tb410Repository tb410Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto040302> findAllByDsl(Search040302 search040302, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040302.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040302.getSYmd2(), "-");

        Page<Tb410> result = tb410Repository.findAllBy040302(ymd1, ymd2, search040302, pageable);

        List<Dto040302> resultList = new ArrayList<>();

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> statusMap = codeToName.commonCode("12");

        for(Tb410 entity : result.getContent()){
            List<Tb410_1> tb410_1s = entity.getTb410_1s();

            String empNm = empMap.get(entity.getEmpNo());
            String custNm = custMap.get(entity.getCustCd());
            String statusNm = statusMap.get(entity.getStatus());

            for(Tb410_1 tb410_1 : tb410_1s){
                String goodsNm = goodsMap.get(tb410_1.getGoodsCd());
                Dto040302 dto040302 = Dto040302.of(entity, tb410_1, custNm, empNm, statusNm, goodsNm);
                resultList.add(dto040302);
            }
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040302 search040302) {
        String ymd1 = GlobalMethod.replaceYmd(search040302.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040302.getSYmd2(), "-");

        int seq = 0;

        List<Tb410> result = tb410Repository.findAllBy040302Excel(ymd1, ymd2, search040302);

        List<List<String>> resultList = new ArrayList<>();

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> statusMap = codeToName.commonCode("12");

        for(Tb410 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb410_1> tb410_1s = entity.getTb410_1s();

            String estNo = entity.getEstimDt().substring(0,4) + "-" + entity.getEstimDt().substring(4,6) + "-" + entity.getEstimDt().substring(6,8) + "-" + entity.getEstimNo();

            String empNm = empMap.get(entity.getEmpNo());
            String custNm = custMap.get(entity.getCustCd());
            String statusNm = statusMap.get(entity.getStatus());

            for(Tb410_1 tb410_1 : tb410_1s){
                String goodsNm = goodsMap.get(tb410_1.getGoodsCd());
                List<String> list = new ArrayList<>();
                list.add(estNo);
                list.add(custNm);
                list.add(empNm);
                list.add(entity.getExpDt());
                list.add(entity.getPayCondit());
                list.add(entity.getNote());
                list.add(statusNm);
                list.add(goodsNm);
                list.add(tb410_1.getStandard());
                list.add(String.valueOf(tb410_1.getQty()));
                list.add(String.valueOf(tb410_1.getPrice()));
                list.add(String.valueOf(tb410_1.getAmt()));
                list.add(String.valueOf(tb410_1.getVat()));

                resultList.add(list);
            }
            seq = entity.getSeq();
        }

        return resultList;
    }
}
