package project.honey.business.service.analyze;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.honey.business.dto.analaze.Dto040306;
import project.honey.business.dto.analaze.PrintData040306;
import project.honey.business.dto.analaze.PrintData040306_1;
import project.honey.business.entity.basic.Tb405;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.entity.manage.Tb412_1;
import project.honey.business.form.analyze.Search040306;
import project.honey.business.repository.basic.Tb405Repository;
import project.honey.business.repository.manage.Tb412Repository;
import project.honey.business.repository.manage.Tb412_1Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalConst;
import project.honey.comm.GlobalMethod;
import project.honey.company.entity.Tb101;
import project.honey.company.repository.Tb101Repository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class Service040306Impl implements Service040306{

    private final Tb412Repository tb412Repository;
    private final Tb412_1Repository tb412_1Repository;
    private final Tb101Repository tb101Repository;
    private final Tb405Repository tb405Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto040306> findAllByDsl(Search040306 search040306, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040306.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040306.getSYmd2(), "-");

        int qty = 0, amt = 0, vat = 0, tot = 0, num = 0;
        String order, goods = null;

        List<Tb412_1> findSeqList = tb412_1Repository.findSeqGoods(search040306.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb412().getSeq()));
        set.forEach(seqList::add);

        Page<Tb412> result = tb412Repository.findAllBy040306(ymd1, ymd2, search040306, seqList, pageable);

        List<Dto040306> resultList = new ArrayList<>();

        Map<String, String> goodsMap = codeToName.goods();

        for(Tb412 entity : result.getContent()){
            List<Tb412_1> tb412_1s = entity.getTb412_1s();

            String goodsNm = goodsMap.get(tb412_1s.get(0).getGoodsCd());

            for(Tb412_1 tb412_1 : tb412_1s){
                goods = goodsNm;
                if(tb412_1s.size() > 1){
                    if(num + 1 == tb412_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                qty += tb412_1.getQty();
                amt += tb412_1.getAmt();
                vat += tb412_1.getVat();
            }
            tot = amt + vat;
            Dto040306 dto = Dto040306.of(entity, goods, qty, amt , vat, tot);
            resultList.add(dto);

            num = 0;
            qty = 0;
            amt = 0;
            vat = 0;
            tot = 0;
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040306 search040306) {
        String ymd1 = GlobalMethod.replaceYmd(search040306.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040306.getSYmd2(), "-");

        int seq = 0, qty = 0, amt = 0, vat = 0, tot = 0, num = 0;
        String order, goods = null;

        List<Tb412_1> findSeqList = tb412_1Repository.findSeqGoods(search040306.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb412().getSeq()));
        set.forEach(seqList::add);

        List<List<String>> resultList = new ArrayList<>();

        List<Tb412> result = tb412Repository.findAllBy040306Excel(ymd1, ymd2, search040306, seqList);

        Map<String, String> goodsMap = codeToName.goods();

        for(Tb412 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb412_1> tb412_1s = entity.getTb412_1s();

            String saleNo = entity.getSaleDt().substring(0,4) + "-" + entity.getSaleDt().substring(4,6) + "-" + entity.getSaleDt().substring(6,8) + "-" + entity.getSaleNo();

            String goodsNm = goodsMap.get(tb412_1s.get(0).getGoodsCd());

            goods = goodsNm;
            for(Tb412_1 tb412_1 : tb412_1s){
                if(tb412_1s.size() > 1){
                    if(num + 1 == tb412_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                qty += tb412_1.getQty();
                amt += tb412_1.getAmt();
                vat += tb412_1.getVat();
            }
            tot = amt + vat;

            List<String> list = new ArrayList<>();
            list.add(saleNo);
            list.add(goods);
            list.add(String.valueOf(qty));
            list.add(String.valueOf(amt));
            list.add(String.valueOf(vat));
            list.add(String.valueOf(tot));

            resultList.add(list);

            num = 0;
            qty = 0;
            amt = 0;
            vat = 0;
            tot = 0;

            seq = entity.getSeq();
        }

        return resultList;
    }

    @Override
    public PrintData040306 findPrintData(Integer id) {
        List<Tb101> tb101s = tb101Repository.findAll();
        Tb101 tb101 = tb101s.get(0);
        Tb412 tb412 = tb412Repository.findById(id).orElseThrow(RuntimeException::new);
        List<Tb412_1> tb412_1s = tb412_1Repository.findByFk(id);

        Map<String, String> custMap = codeToName.cust();
        String custNm = custMap.get(tb412.getCustCd());
        Map<String, String> goodsMap = codeToName.goods();

        List<PrintData040306_1> printData040306_1s = new ArrayList<>();
        GlobalConst globalConst = new GlobalConst();
        tb412_1s.forEach(e -> {
            Tb405 tb405 = tb405Repository.findByGoodsCd(e.getGoodsCd()).orElseThrow(RuntimeException::new);
            PrintData040306_1 printData040306_1 = PrintData040306_1.of(e, goodsMap.get(e.getGoodsCd()), tb405.getUnit());
            printData040306_1s.add(printData040306_1);
        });
        if(tb412_1s.size() < globalConst.getSubInputIdx()){
            for (int i = tb412_1s.size(); i < globalConst.getSubInputIdx(); i++){
                printData040306_1s.add(new PrintData040306_1());
            }
        }

        PrintData040306 resultList = PrintData040306.of(tb101, tb412, printData040306_1s, custNm);
        return resultList;
    }
}
