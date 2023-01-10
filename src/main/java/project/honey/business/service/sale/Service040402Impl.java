package project.honey.business.service.sale;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.sale.*;
import project.honey.business.entity.sale.Tb416;
import project.honey.business.entity.sale.Tb416_1;
import project.honey.business.form.sale.*;
import project.honey.business.repository.basic.Tb402Repository;
import project.honey.business.repository.sale.Tb416Repository;
import project.honey.business.repository.sale.Tb416_1Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service040402Impl implements Service040402{

    private final Tb416Repository tb416Repository;
    private final Tb416_1Repository tb416_1Repository;
    private final Tb402Repository tb402Repository;
    private final CodeToName codeToName;

    @Transactional
    @Override
    public Boolean insert(Tb416Form tb416Form, List<Tb416_1Form> tb416_1Form) {
        Tb416 tb416 = Tb416Form.toTb416(tb416Form);
        Tb416 fk = tb416Repository.save(tb416);
        tb416_1Form.forEach(e -> {
            if(!e.getGoodsCd().isEmpty()){
                Tb416_1 tb416_1 = Tb416_1Form.toTb416_1(e,fk);
                tb416_1Repository.save(tb416_1);
            }
        });
        return true;
    }

    @Override
    public Page<Tb416MainDto> findAllByDsl(Search040402 search040402, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040402.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040402.getSYmd2(), "-");

        int qty = 0, price = 0, num = 0;
        String goods = null;

        List<String> custList = tb402Repository.findAllByClass(search040402.getSCustGr());

        List<Tb416_1> findSeqList = tb416_1Repository.findSeqGoods(search040402.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb416().getSeq()));
        set.forEach(seqList::add);

        Page<Tb416> result = tb416Repository.findAllByDsl(ymd1, ymd2, search040402, seqList, custList, pageable);

        List<Tb416MainDto> resultList = new ArrayList<>();

        Map<String, String> custMap = codeToName.cust();
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> saleTypeMap = codeToName.commonCode("11");
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> whouseMap = codeToName.wHouse();

        for(Tb416 entity : result.getContent()){
            List<Tb416_1> tb416_1s = entity.getTb416_1s();

            String custNm = custMap.get(entity.getCustCd());
            String goodsNm = goodsMap.get(tb416_1s.get(0).getGoodsCd());
            String saleType = saleTypeMap.get(entity.getSaleType());
            String statusNm = statusMap.get(entity.getStatus());
            String whouseNm = whouseMap.get(entity.getWhouseCd());

            for(Tb416_1 tb416_1 : tb416_1s){
                goods = goodsNm;
                if(tb416_1s.size() > 1){
                    if(num + 1 == tb416_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                qty += tb416_1.getQty();
                price += tb416_1.getAmt();
                price += tb416_1.getVat();
            }
            Tb416MainDto dto = Tb416MainDto.of(entity, custNm, goods, price, qty, saleType, whouseNm, statusNm);
            resultList.add(dto);
            num = 0;
            qty = 0;
            price = 0;
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040402 search040402) {
        String ymd1 = GlobalMethod.replaceYmd(search040402.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040402.getSYmd2(), "-");

        int qty = 0, price = 0, num = 0, seq = 0;
        String goods = null;

        List<String> custList = tb402Repository.findAllByClass(search040402.getSCustGr());

        List<Tb416_1> findSeqList = tb416_1Repository.findSeqGoods(search040402.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb416().getSeq()));
        set.forEach(seqList::add);

        List<Tb416> result = tb416Repository.findAllByExcel(ymd1, ymd2, search040402, custList, seqList);

        List<List<String>> resultList = new ArrayList<>();

        Map<String, String> custMap = codeToName.cust();
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> saleTypeMap = codeToName.commonCode("11");
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> whouseMap = codeToName.wHouse();

        for(Tb416 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb416_1> tb416_1s = entity.getTb416_1s();
            String prt = "인쇄전";

            String buyNo = entity.getBuyDt().substring(0,4) + "-" + entity.getBuyDt().substring(4,6) + "-" + entity.getBuyDt().substring(6,8) + "-" + entity.getBuyNo();

            String custNm = custMap.get(entity.getCustCd());
            String goodsNm = goodsMap.get(tb416_1s.get(0).getGoodsCd());
            String saleType = saleTypeMap.get(entity.getSaleType());
            String statusNm = statusMap.get(entity.getStatus());
            String whouseNm = whouseMap.get(entity.getWhouseCd());

            if(!entity.getPrtEmp().isEmpty() && !entity.getPrtDt().isEmpty()) prt = "인쇄함";
            goods = goodsNm;
            for(Tb416_1 tb416_1 : tb416_1s){
                if(tb416_1s.size() > 1){
                    if(num + 1 == tb416_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                qty += tb416_1.getQty();
                price += tb416_1.getAmt();
                price += tb416_1.getVat();
            }

            List<String> list = new ArrayList<>();
            list.add(buyNo);
            list.add(custNm);
            list.add(goods);
            list.add(String.valueOf(price));
            list.add(String.valueOf(qty));
            list.add(saleType);
            list.add(whouseNm);
            list.add(statusNm);
            list.add(prt);

            resultList.add(list);

            qty = 0;
            price = 0;
            num = 0;

            seq = entity.getSeq();
        }

        return resultList;
    }

    @Override
    public Tb416Dto findById(Integer id) {
        Tb416 entity = tb416Repository.findById(id).orElseThrow(RuntimeException::new);
        Map<String, String> custMap = codeToName.cust();
        String custNm = custMap.get(entity.getCustCd());
        return Tb416Dto.of(entity, custNm);
    }

    @Override
    public List<Tb416_1Dto> findChildByFk(Integer id) {
        List<Tb416_1Dto> resultList = new ArrayList<>();
        List<Tb416_1> result = tb416_1Repository.findByFk(id);
        Map<String, String> goodsMap = codeToName.goods();
        result.forEach(e -> {
            String goodsNm = goodsMap.get(e.getGoodsCd());
            Tb416_1Dto dto = Tb416_1Dto.of(e, goodsNm);
            resultList.add(dto);
        });
        return resultList;
    }

    @Override
    public Long findBuyNo() {
        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Long result = tb416Repository.findBuyNo(nowDate);
        return result + 1;
    }

    @Transactional
    @Override
    public Boolean update(Tb416Form tb416Form, List<Tb416_1Form> tb416_1Form) {
        Tb416 entity = tb416Repository.findById(tb416Form.getSeq()).orElseThrow(RuntimeException::new);
        List<Tb416_1> tb416_1s = tb416_1Form.stream().filter(e -> !e.getGoodsCd().isEmpty()).map(e -> Tb416_1Form.toTb416_1(e, entity)).collect(Collectors.toList());
        entity.updateData(tb416Form, tb416_1s);
        return true;
    }

    @Transactional
    @Override
    public Boolean delete(Integer id) {
        tb416_1Repository.deleteByFk(id);
        tb416Repository.deleteById(id);
        return true;
    }
}
