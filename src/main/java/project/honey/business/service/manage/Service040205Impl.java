package project.honey.business.service.manage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.manage.*;
import project.honey.business.entity.manage.Tb414;
import project.honey.business.entity.manage.Tb414_1;
import project.honey.business.form.manage.*;
import project.honey.business.repository.manage.Tb414Repository;
import project.honey.business.repository.manage.Tb414_1Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service040205Impl implements Service040205{

    private final Tb414Repository tb414Repository;
    private final Tb414_1Repository tb414_1Repository;
    private final CodeToName codeToName;

    @Transactional
    @Override
    public Boolean insert(Tb414Form tb414Form, List<Tb414_1Form> tb414_1Form) {
        Tb414 tb414 = Tb414Form.toTb414(tb414Form);
        Tb414 fk = tb414Repository.save(tb414);
        tb414_1Form.forEach(e -> {
            if(!e.getGoodsCd().isEmpty()){
                Tb414_1 tb414_1 = Tb414_1Form.toTb414_1(e,fk);
                tb414_1Repository.save(tb414_1);
            }
        });
        return true;
    }

    @Override
    public Page<Tb414MainDto> findAllByDsl(Search040205 search040205, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040205.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040205.getSYmd2(), "-");

        int qty = 0, num = 0;
        String goods = null;

        List<Tb414_1> findSeqList = tb414_1Repository.findSeqGoods(search040205.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb414().getSeq()));
        set.forEach(seqList::add);

        Page<Tb414> result = tb414Repository.findAllByDsl(ymd1, ymd2, search040205, seqList, pageable);

        List<Tb414MainDto> resultList = new ArrayList<>();

        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> wHouseMap = codeToName.wHouse();

        for(Tb414 entity : result.getContent()){
            List<Tb414_1> tb414_1s = entity.getTb414_1s();

            String goodsNm = goodsMap.get(tb414_1s.get(0).getGoodsCd());
            String custNm = custMap.get(entity.getCustCd());
            String whouseNm = wHouseMap.get(entity.getWhouseCd());

            for(Tb414_1 tb414_1 : tb414_1s){
                goods = goodsNm;
                if(tb414_1s.size() > 1){
                    if(num + 1 == tb414_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                qty += tb414_1.getQty();
            }
            Tb414MainDto dto = Tb414MainDto.of(entity, whouseNm, custNm, goods, qty);
            resultList.add(dto);
            num = 0;
            qty = 0;
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040205 search040205) {
        String ymd1 = GlobalMethod.replaceYmd(search040205.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040205.getSYmd2(), "-");

        int num = 0, seq = 0, qty = 0;
        String goods = null;

        List<Tb414_1> findSeqList = tb414_1Repository.findSeqGoods(search040205.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb414().getSeq()));
        set.forEach(seqList::add);

        List<List<String>> resultList = new ArrayList<>();

        List<Tb414> result = tb414Repository.findAllByExcel(ymd1, ymd2, search040205, seqList);

        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> wHouseMap = codeToName.wHouse();

        for(Tb414 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb414_1> tb414_1s = entity.getTb414_1s();
            String prt = "인쇄전";

            String shipNo = entity.getShipDt().substring(0,4) + "-" + entity.getShipDt().substring(4,6) + "-" + entity.getShipDt().substring(6,8) + "-" + entity.getShipNo();

            String goodsNm = goodsMap.get(tb414_1s.get(0).getGoodsCd());
            String custNm = custMap.get(entity.getCustCd());
            String whouseNm = wHouseMap.get(entity.getWhouseCd());

            if(!entity.getPrtEmp().isEmpty() && !entity.getPrtDt().isEmpty()) prt = "인쇄함";
            goods = goodsNm;
            for(Tb414_1 tb414_1 : tb414_1s){
                if(tb414_1s.size() > 1){
                    if(num + 1 == tb414_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                qty += tb414_1.getQty();
            }

            List<String> list = new ArrayList<>();
            list.add(shipNo);
            list.add(whouseNm);
            list.add(custNm);
            list.add(goods);
            list.add(String.valueOf(qty));
            list.add(prt);

            resultList.add(list);

            qty = 0;
            num = 0;

            seq = entity.getSeq();
        }

        return resultList;
    }

    @Override
    public Tb414Dto findById(Integer id) {
        Tb414 entity = tb414Repository.findById(id).orElseThrow(RuntimeException::new);
        Map<String, String> custMap = codeToName.cust();
        String custNm = custMap.get(entity.getCustCd());
        return Tb414Dto.of(entity, custNm);
    }

    @Override
    public List<Tb414_1Dto> findChildByFk(Integer id) {
        List<Tb414_1Dto> resultList = new ArrayList<>();
        List<Tb414_1> result = tb414_1Repository.findByFk(id);
        Map<String, String> goodsMap = codeToName.goods();
        result.forEach(e -> {
            String goodsNm = goodsMap.get(e.getGoodsCd());
            Tb414_1Dto dto = Tb414_1Dto.of(e, goodsNm);
            resultList.add(dto);
        });
        return resultList;
    }

    @Override
    public Long findShipNo() {
        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Long result = tb414Repository.findShipNo(nowDate);
        return result + 1;
    }

    @Transactional
    @Override
    public Boolean update(Tb414Form tb414Form, List<Tb414_1Form> tb414_1Form) {
        Tb414 entity = tb414Repository.findById(tb414Form.getSeq()).orElseThrow(RuntimeException::new);
        List<Tb414_1> tb414_1s = tb414_1Form.stream().filter(e -> !e.getGoodsCd().isEmpty()).map(e -> Tb414_1Form.toTb414_1(e, entity)).collect(Collectors.toList());
        entity.updateData(tb414Form, tb414_1s);
        return true;
    }

    @Transactional
    @Override
    public Boolean delete(Integer id) {
        tb414_1Repository.deleteByFk(id);
        tb414Repository.deleteById(id);
        return true;
    }
}
