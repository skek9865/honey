package project.honey.business.service.sale;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.manage.Tb412MainDto;
import project.honey.business.dto.sale.PopUp040401Dto;
import project.honey.business.dto.sale.Tb415Dto;
import project.honey.business.dto.sale.Tb415MainDto;
import project.honey.business.dto.sale.Tb415_1Dto;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.entity.manage.Tb412_1;
import project.honey.business.entity.sale.Tb415;
import project.honey.business.entity.sale.Tb415_1;
import project.honey.business.form.sale.Search040401;
import project.honey.business.form.sale.Tb415Form;
import project.honey.business.form.sale.Tb415_1Form;
import project.honey.business.repository.sale.Tb415Repository;
import project.honey.business.repository.sale.Tb415_1Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service040401Impl implements Service040401{

    private final Tb415Repository tb415Repository;
    private final Tb415_1Repository tb415_1Repository;
    private final CodeToName codeToName;

    @Transactional
    @Override
    public Boolean insert(Tb415Form tb415Form, List<Tb415_1Form> tb415_1Form) {
        Tb415 tb415 = Tb415Form.toTb415(tb415Form);
        Tb415 fk = tb415Repository.save(tb415);
        tb415_1Form.forEach(e -> {
            if(!e.getGoodsCd().isEmpty()){
                Tb415_1 tb415_1 = Tb415_1Form.toTb415_1(e,fk);
                tb415_1Repository.save(tb415_1);
            }
        });
        return true;
    }

    @Override
    public Page<Tb415MainDto> findAllByDsl(Search040401 search040401, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040401.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040401.getSYmd2(), "-");

        int price = 0, num = 0;
        String goods = null;

        List<Tb415_1> findSeqList = tb415_1Repository.findSeqGoods(search040401.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb415().getSeq()));
        set.forEach(seqList::add);

        Page<Tb415> result = tb415Repository.findAllByDsl(ymd1, ymd2, search040401, seqList, pageable);

        List<Tb415MainDto> resultList = new ArrayList<>();

        Map<String, String> custMap = codeToName.cust();
        Map<String, String> empMap = codeToName.emp();
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> statusMap = codeToName.commonCode("12");

        for(Tb415 entity : result.getContent()){
            List<Tb415_1> tb415_1s = entity.getTb415_1s();

            String custNm = custMap.get(entity.getCustCd());
            String empNm = empMap.get(entity.getEmpNo());
            String goodsNm = goodsMap.get(tb415_1s.get(0).getGoodsCd());
            String statusNm = statusMap.get(entity.getStatus());

            for(Tb415_1 tb415_1 : tb415_1s){
                goods = goodsNm;
                if(tb415_1s.size() > 1){
                    if(num + 1 == tb415_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                price += tb415_1.getAmt();
                price += tb415_1.getVat();
            }
            Tb415MainDto dto = Tb415MainDto.of(entity, custNm, empNm, goods, price, statusNm);
            resultList.add(dto);
            num = 0;
            price = 0;
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040401 search040401) {
        String ymd1 = GlobalMethod.replaceYmd(search040401.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040401.getSYmd2(), "-");

        int num = 0, seq = 0, price = 0;
        String goods = null;

        List<Tb415_1> findSeqList = tb415_1Repository.findSeqGoods(search040401.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb415().getSeq()));
        set.forEach(seqList::add);

        List<List<String>> resultList = new ArrayList<>();

        List<Tb415> result = tb415Repository.findAllByExcel(ymd1, ymd2, search040401, seqList);

        Map<String, String> custMap = codeToName.cust();
        Map<String, String> empMap = codeToName.emp();
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> statusMap = codeToName.commonCode("12");

        for(Tb415 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb415_1> tb415_1s = entity.getTb415_1s();
            String prt = "인쇄전";

            String orderNo = entity.getOrderDt().substring(0,4) + "-" + entity.getOrderDt().substring(4,6) + "-" + entity.getOrderDt().substring(6,8) + "-" + entity.getOrderNo();
            String deadDt = entity.getDeadDt().substring(0,4) + "-" + entity.getDeadDt().substring(4,6) + "-" + entity.getDeadDt().substring(6,8);

            String custNm = custMap.get(entity.getCustCd());
            String empNm = empMap.get(entity.getEmpNo());
            String goodsNm = goodsMap.get(tb415_1s.get(0).getGoodsCd());
            String statusNm = statusMap.get(entity.getStatus());

            if(!entity.getPrtEmp().isEmpty() && !entity.getPrtDt().isEmpty()) prt = "인쇄함";
            goods = goodsNm;
            for(Tb415_1 tb415_1 : tb415_1s){
                if(tb415_1s.size() > 1){
                    if(num + 1 == tb415_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                price += tb415_1.getAmt();
                price += tb415_1.getVat();
            }

            List<String> list = new ArrayList<>();
            list.add(orderNo);
            list.add(custNm);
            list.add(empNm);
            list.add(goods);
            list.add(deadDt);
            list.add(String.valueOf(price));
            list.add(statusNm);
            list.add(prt);

            resultList.add(list);

            price = 0;
            num = 0;

            seq = entity.getSeq();
        }

        return resultList;
    }

    @Override
    public Tb415Dto findById(Integer id) {
        Tb415 entity = tb415Repository.findById(id).orElseThrow(RuntimeException::new);
        Map<String, String> custMap = codeToName.cust();
        String custNm = custMap.get(entity.getCustCd());
        return Tb415Dto.of(entity, custNm);
    }

    @Override
    public List<Tb415_1Dto> findChildByFk(Integer id) {
        List<Tb415_1Dto> resultList = new ArrayList<>();
        List<Tb415_1> result = tb415_1Repository.findByFk(id);
        Map<String, String> goodsMap = codeToName.goods();
        result.forEach(e -> {
            String goodsNm = goodsMap.get(e.getGoodsCd());
            Tb415_1Dto dto = Tb415_1Dto.of(e, goodsNm);
            resultList.add(dto);
        });
        return resultList;
    }

    @Override
    public Long findOrderNo() {
        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Long result = tb415Repository.findOrderNo(nowDate);
        return result + 1;
    }

    @Transactional
    @Override
    public Boolean update(Tb415Form tb415Form, List<Tb415_1Form> tb415_1Form) {
        Tb415 entity = tb415Repository.findById(tb415Form.getSeq()).orElseThrow(RuntimeException::new);
        List<Tb415_1> tb415_1s = tb415_1Form.stream().filter(e -> !e.getGoodsCd().isEmpty()).map(e -> Tb415_1Form.toTb415_1(e, entity)).collect(Collectors.toList());
        entity.updateData(tb415Form, tb415_1s);
        return true;
    }

    @Transactional
    @Override
    public Boolean delete(Integer id) {
        tb415_1Repository.deleteByFk(id);
        tb415Repository.deleteById(id);
        return true;
    }

    @Override
    public List<PopUp040401Dto> findAllByPopUp(SearchPopUp410 searchPopUp410) {
        String ymd1 = GlobalMethod.replaceYmd(searchPopUp410.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(searchPopUp410.getSYmd2(), "-");

        int qty = 0, num = 0, seq = 0;
        String goods = null;

        List<Tb415_1> findSeqList = tb415_1Repository.findSeqGoods(searchPopUp410.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb415().getSeq()));
        set.forEach(seqList::add);

        List<Tb415> result = tb415Repository.findAllByPopUp(ymd1, ymd2, searchPopUp410, seqList);

        List<PopUp040401Dto> resultList = new ArrayList<>();

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();

        for(Tb415 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb415_1> tb415_1s = entity.getTb415_1s();

            String goodsNm = goodsMap.get(tb415_1s.get(0).getGoodsCd());
            String empNm = empMap.get(entity.getEmpNo());
            String custNm = custMap.get(entity.getCustCd());

            for(Tb415_1 tb415_1 : tb415_1s){
                goods = goodsNm;
                if(tb415_1s.size() > 1){
                    if(num + 1 == tb415_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                qty += tb415_1.getQty();
            }
            PopUp040401Dto dto = PopUp040401Dto.of(entity, custNm, empNm, goods, qty);
            resultList.add(dto);
            num = 0;
            qty = 0;

            seq = entity.getSeq();
        }

        return resultList;
    }
}
