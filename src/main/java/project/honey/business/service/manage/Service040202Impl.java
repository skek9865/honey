package project.honey.business.service.manage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.manage.*;
import project.honey.business.entity.manage.Tb411;
import project.honey.business.entity.manage.Tb411_1;
import project.honey.business.form.manage.Search040201;
import project.honey.business.form.manage.Tb411Form;
import project.honey.business.form.manage.Tb411_1Form;
import project.honey.business.repository.manage.Tb411Repository;
import project.honey.business.repository.manage.Tb411_1Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class Service040202Impl implements Service040202{

    private final Tb411Repository tb411Repository;
    private final Tb411_1Repository tb411_1Repository;
    private final CodeToName codeToName;

    @Transactional
    @Override
    public Boolean insert(Tb411Form tb411Form, List<Tb411_1Form> tb411_1Form) {
        Tb411 tb411 = Tb411Form.toTb411(tb411Form);
        Tb411 fk = tb411Repository.save(tb411);
        tb411_1Form.forEach(e -> {
            if(!e.getGoodsCd().isEmpty()){
                Tb411_1 tb411_1 = Tb411_1Form.toTb411_1(e,fk.getSeq());
                tb411_1Repository.save(tb411_1);
            }
        });
        return true;
    }

    @Override
    public Page<Tb411MainDto> findAllByDsl(Search040201 search040201, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040201.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040201.getSYmd2(), "-");

        int qty = 0, price = 0, num = 0;
        String order, goods = null;

        List<Tb411_1> findSeqList = tb411_1Repository.findSeqGoods(search040201.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb411().getSeq()));
        set.forEach(seqList::add);

        Page<Tb411> result = tb411Repository.findAllByDsl(ymd1, ymd2, search040201, seqList, pageable);

        List<Tb411MainDto> resultList = new ArrayList<>();

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();

        for(Tb411 entity : result.getContent()){
            List<Tb411_1> tb411_1s = entity.getTb411_1s();
            String prt = "인쇄전";

            String goodsNm = goodsMap.get(tb411_1s.get(0).getGoodsCd());
            String empNm = empMap.get(entity.getEmpNo());
            String statusNm = statusMap.get(entity.getStatus());
            String custNm = custMap.get(entity.getCustCd());

            if(entity.getPrtEmp() != null && entity.getPrtDt() != null) prt = "인쇄함";
            for(Tb411_1 tb411_1 : tb411_1s){
                goods = goodsNm;
                if(tb411_1s.size() > 1){
                    if(num + 1 == tb411_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                price += tb411_1.getAmt();
                price += tb411_1.getVat();
                qty += tb411_1.getQty();
            }
            Tb411MainDto dto = Tb411MainDto.of(entity, custNm, goods, empNm, price, qty, prt, statusNm);
            resultList.add(dto);
            num = 0;
            price = 0;
            qty = 0;
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040201 search040201) {
        String ymd1 = GlobalMethod.replaceYmd(search040201.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040201.getSYmd2(), "-");

        int price = 0, num = 0, seq = 0, qty = 0;
        String order, goods = null;

        List<Tb411_1> findSeqList = tb411_1Repository.findSeqGoods(search040201.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb411().getSeq()));
        set.forEach(seqList::add);

        List<List<String>> resultList = new ArrayList<>();

        List<Tb411> result = tb411Repository.findAllByExcel(ymd1, ymd2, search040201, seqList);

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();

        for(Tb411 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb411_1> tb411_1s = entity.getTb411_1s();
            String prt = "인쇄전";

            String orderDt = entity.getOrderDt();
            String fdt = orderDt.substring(0,4);
            String sdt = orderDt.substring(4,6);
            String tdt = orderDt.substring(6,8);

            order = fdt + "-" + sdt + "-" + tdt + "-" + entity.getOrderNo();

            String goodsNm = goodsMap.get(tb411_1s.get(0).getGoodsCd());
            String empNm = empMap.get(entity.getEmpNo());
            String statusNm = statusMap.get(entity.getStatus());
            String custNm = custMap.get(entity.getCustCd());

            if(!entity.getPrtEmp().isEmpty() && !entity.getPrtDt().isEmpty()) prt = "인쇄함";
            goods = goodsNm;
            for(Tb411_1 tb411_1 : tb411_1s){
                if(tb411_1s.size() > 1){
                    if(num + 1 == tb411_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                price += tb411_1.getAmt();
                price += tb411_1.getVat();
                qty += tb411_1.getQty();
            }

            List<String> list = new ArrayList<>();
            list.add(order);
            list.add(custNm);
            list.add(empNm);
            list.add(goods);
            list.add(String.valueOf(price));
            list.add(String.valueOf(qty));
            list.add(statusNm);
            list.add(prt);
            list.add(entity.getNote());

            resultList.add(list);

            price = 0;
            qty = 0;
            num = 0;

            seq = entity.getSeq();
        }

        return resultList;
    }

    @Override
    public Tb411Dto findById(Integer id) {
        Tb411 entity = tb411Repository.findById(id).orElseThrow(RuntimeException::new);
        Map<String, String> custMap = codeToName.cust();
        String custNm = custMap.get(entity.getCustCd());
        return Tb411Dto.of(entity, custNm);
    }

    @Override
    public List<Tb411_1Dto> findChildByFk(Integer id) {
        List<Tb411_1Dto> resultList = new ArrayList<>();
        List<Tb411_1> result = tb411_1Repository.findByFk(id);
        Map<String, String> goodsMap = codeToName.goods();
        result.forEach(e -> {
            String goodsNm = goodsMap.get(e.getGoodsCd());
            Tb411_1Dto dto = Tb411_1Dto.of(e, goodsNm);
            resultList.add(dto);
        });
        return resultList;
    }

    @Override
    public Long findOrderNo() {
        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Long result = tb411Repository.findOrderNo(nowDate);
        return result + 1;
    }

    @Transactional
    @Override
    public Boolean update(Tb411Form tb411Form, List<Tb411_1Form> tb411_1Form) {
        Tb411 entity = tb411Repository.findById(tb411Form.getSeq()).orElseThrow(RuntimeException::new);
        List<Tb411_1> tb411_1s = tb411_1Form.stream().filter(e -> !e.getGoodsCd().isEmpty()).map(e -> Tb411_1Form.toTb411_1(e, entity.getSeq())).collect(Collectors.toList());
        entity.updateData(tb411Form, tb411_1s);
        return true;
    }

    @Transactional
    @Override
    public Boolean delete(Integer id) {
        tb411_1Repository.deleteByFk(id);
        tb411Repository.deleteById(id);
        return true;
    }

    @Override
    public PrintData040201 findPrintData(Integer id) {
        return null;
    }
}
