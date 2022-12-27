package project.honey.business.service.analyze;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.honey.business.dto.analaze.Dto040303;
import project.honey.business.entity.manage.Tb411;
import project.honey.business.entity.manage.Tb411_1;
import project.honey.business.form.analyze.Search040302;
import project.honey.business.repository.manage.Tb411Repository;
import project.honey.business.repository.manage.Tb411_1Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.util.*;

@Service
@RequiredArgsConstructor
public class Service040303Impl implements Service040303{

    private final Tb411Repository tb411Repository;
    private final Tb411_1Repository tb411_1Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto040303> findAllByDsl(Search040302 search040302, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040302.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040302.getSYmd2(), "-");

        List<Tb411_1> findSeqList = tb411_1Repository.findSeqGoods(search040302.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb411().getSeq()));
        set.forEach(seqList::add);

        Page<Tb411> result = tb411Repository.findAllBy040303(ymd1, ymd2, search040302, seqList, pageable);

        List<Dto040303> resultList = new ArrayList<>();

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> statusMap = codeToName.commonCode("12");

        for(Tb411 entity : result.getContent()){
            List<Tb411_1> tb411_1s = entity.getTb411_1s();

            String empNm = empMap.get(entity.getEmpNo());
            String custNm = custMap.get(entity.getCustCd());
            String statusNm = statusMap.get(entity.getStatus());

            for(Tb411_1 tb411_1 : tb411_1s){
                if(StringUtils.hasText(search040302.getSGoodsCd()) && !tb411_1.getGoodsCd().equals(search040302.getSGoodsCd())) continue;
                String goodsNm = goodsMap.get(tb411_1.getGoodsCd());
                Dto040303 dto040303 = Dto040303.of(entity, tb411_1, custNm, empNm, statusNm, goodsNm);
                resultList.add(dto040303);
            }
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040302 search040302) {
        String ymd1 = GlobalMethod.replaceYmd(search040302.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040302.getSYmd2(), "-");

        int seq = 0;

        List<Tb411_1> findSeqList = tb411_1Repository.findSeqGoods(search040302.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb411().getSeq()));
        set.forEach(seqList::add);

        List<Tb411> result = tb411Repository.findAllBy040303Excel(ymd1, ymd2, search040302, seqList);

        List<List<String>> resultList = new ArrayList<>();

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> statusMap = codeToName.commonCode("12");

        for(Tb411 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb411_1> tb411_1s = entity.getTb411_1s();

            String orderNo = entity.getOrderDt().substring(0,4) + "-" + entity.getOrderDt().substring(4,6) + "-" + entity.getOrderDt().substring(6,8) + "-" + entity.getOrderNo();
            String deadDt = entity.getDeadDt().substring(0,4) + "-" + entity.getDeadDt().substring(4,6) + "-" + entity.getDeadDt().substring(6,8);

            String empNm = empMap.get(entity.getEmpNo());
            String custNm = custMap.get(entity.getCustCd());
            String statusNm = statusMap.get(entity.getStatus());

            for(Tb411_1 tb411_1 : tb411_1s){
                if(StringUtils.hasText(search040302.getSGoodsCd()) && !tb411_1.getGoodsCd().equals(search040302.getSGoodsCd())) continue;
                String goodsNm = goodsMap.get(tb411_1.getGoodsCd());
                List<String> list = new ArrayList<>();
                list.add(orderNo);
                list.add(custNm);
                list.add(empNm);
                list.add(entity.getExpDt());
                list.add(entity.getPayCondit());
                list.add(entity.getNote());
                list.add(statusNm);
                list.add(goodsNm);
                list.add(tb411_1.getStandard());
                list.add(String.valueOf(tb411_1.getQty()));
                list.add(String.valueOf(tb411_1.getPrice()));
                list.add(String.valueOf(tb411_1.getAmt()));
                list.add(String.valueOf(tb411_1.getVat()));
                list.add(deadDt);

                resultList.add(list);
            }
            seq = entity.getSeq();
        }

        return resultList;
    }
}
