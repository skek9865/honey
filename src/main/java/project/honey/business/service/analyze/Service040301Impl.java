package project.honey.business.service.analyze;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import project.honey.business.dto.analaze.Dto040301;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.entity.manage.Tb412_1;
import project.honey.business.form.analyze.Search040301;
import project.honey.business.repository.basic.Tb402Repository;
import project.honey.business.repository.manage.Tb412Repository;
import project.honey.business.repository.manage.Tb412_1Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.util.*;

@Service
@RequiredArgsConstructor
public class Service040301Impl implements Service040301{

    private final Tb412Repository tb412Repository;
    private final Tb412_1Repository tb412_1Repository;
    private final Tb402Repository tb402Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto040301> findAllByDsl(Search040301 search040301, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040301.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040301.getSYmd2(), "-");

        List<Tb412_1> findSeqList = tb412_1Repository.findSeqGoods(search040301.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb412().getSeq()));
        set.forEach(seqList::add);

        List<String> custList = tb402Repository.findAllByClass(search040301.getSCustGr());

        Page<Tb412> result = tb412Repository.findAllBy040301(ymd1, ymd2, search040301, seqList, custList, pageable);

        List<Dto040301> resultList = new ArrayList<>();

        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> projectMap = codeToName.project();

        for(Tb412 entity : result.getContent()){
            List<Tb412_1> tb412_1s = entity.getTb412_1s();

            String custNm = custMap.get(entity.getCustCd());
            String projectNm = projectMap.get(entity.getProjectCd());

            for(Tb412_1 tb412_1 : tb412_1s){
                if(StringUtils.hasText(search040301.getSGoodsCd()) && !tb412_1.getGoodsCd().equals(search040301.getSGoodsCd())) continue;
                String goodsNm = goodsMap.get(tb412_1.getGoodsCd());
                Dto040301 dto040301 = Dto040301.of(entity, tb412_1, goodsNm, custNm, projectNm);
                resultList.add(dto040301);
            }
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040301 search040301) {
        String ymd1 = GlobalMethod.replaceYmd(search040301.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040301.getSYmd2(), "-");

        int seq = 0;

        List<Tb412_1> findSeqList = tb412_1Repository.findSeqGoods(search040301.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb412().getSeq()));
        set.forEach(seqList::add);

        List<String> custList = tb402Repository.findAllByClass(search040301.getSCustGr());

        List<Tb412> result = tb412Repository.findAllBy040301Excel(ymd1, ymd2, search040301, seqList, custList);

        List<List<String>> resultList = new ArrayList<>();

        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> projectMap = codeToName.project();

        for(Tb412 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb412_1> tb412_1s = entity.getTb412_1s();

            String saleNo = entity.getSaleDt().substring(0,4) + "-" + entity.getSaleDt().substring(4,6) + "-" + entity.getSaleDt().substring(6,8) + "-" + entity.getSaleNo();

            String custNm = custMap.get(entity.getCustCd());
            String projectNm = projectMap.get(entity.getProjectCd());

            for(Tb412_1 tb412_1 : tb412_1s){
                if(StringUtils.hasText(search040301.getSGoodsCd()) && !tb412_1.getGoodsCd().equals(search040301.getSGoodsCd())) continue;
                String goodsNm = goodsMap.get(tb412_1.getGoodsCd());
                List<String> list = new ArrayList<>();
                list.add(saleNo);
                list.add(goodsNm);
                list.add(tb412_1.getStandard());
                list.add(String.valueOf(tb412_1.getQty()));
                list.add(String.valueOf(tb412_1.getPrice()));
                list.add(String.valueOf(tb412_1.getAmt()));
                list.add(String.valueOf(tb412_1.getVat()));
                list.add(custNm);
                list.add(projectNm);
                list.add(entity.getNote());
                list.add(entity.getNote2());

                resultList.add(list);
            }
            seq = entity.getSeq();
        }

        return resultList;
    }
}
