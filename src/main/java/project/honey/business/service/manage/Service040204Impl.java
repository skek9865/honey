package project.honey.business.service.manage;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.manage.*;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.entity.manage.Tb412_1;
import project.honey.business.entity.manage.Tb413;
import project.honey.business.entity.manage.Tb413_1;
import project.honey.business.form.manage.*;
import project.honey.business.repository.manage.Tb413Repository;
import project.honey.business.repository.manage.Tb413_1Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service040204Impl implements Service040204{

    private final Tb413Repository tb413Repository;
    private final Tb413_1Repository tb413_1Repository;
    private final CodeToName codeToName;

    @Transactional
    @Override
    public Boolean insert(Tb413Form tb413Form, List<Tb413_1Form> tb413_1Form) {
        Tb413 tb413 = Tb413Form.toTb413(tb413Form);
        Tb413 fk = tb413Repository.save(tb413);
        tb413_1Form.forEach(e -> {
            if(!e.getGoodsCd().isEmpty()){
                Tb413_1 tb413_1 = Tb413_1Form.toTb413_1(e,fk);
                tb413_1Repository.save(tb413_1);
            }
        });
        return true;
    }

    @Override
    public Page<Tb413MainDto> findAllByDsl(Search040204 search040204, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040204.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040204.getSYmd2(), "-");

        int qty = 0, num = 0;
        String goods = null;

        List<Tb413_1> findSeqList = tb413_1Repository.findSeqGoods(search040204.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb413().getSeq()));
        set.forEach(seqList::add);

        Page<Tb413> result = tb413Repository.findAllByDsl(ymd1, ymd2, search040204, seqList, pageable);

        List<Tb413MainDto> resultList = new ArrayList<>();

        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> wHouseMap = codeToName.wHouse();

        for(Tb413 entity : result.getContent()){
            List<Tb413_1> tb413_1s = entity.getTb413_1s();

            String goodsNm = goodsMap.get(tb413_1s.get(0).getGoodsCd());
            String custNm = custMap.get(entity.getCustCd());
            String whouseNm = wHouseMap.get(entity.getWhouseCd());

            for(Tb413_1 tb413_1 : tb413_1s){
                goods = goodsNm;
                if(tb413_1s.size() > 1){
                    if(num + 1 == tb413_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                qty += tb413_1.getQty();
            }
            Tb413MainDto dto = Tb413MainDto.of(entity, whouseNm, custNm, goods, qty);
            resultList.add(dto);
            num = 0;
            qty = 0;
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040204 search040204) {
        String ymd1 = GlobalMethod.replaceYmd(search040204.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040204.getSYmd2(), "-");

        int num = 0, seq = 0, qty = 0;
        String goods = null;

        List<Tb413_1> findSeqList = tb413_1Repository.findSeqGoods(search040204.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb413().getSeq()));
        set.forEach(seqList::add);

        List<List<String>> resultList = new ArrayList<>();

        List<Tb413> result = tb413Repository.findAllByExcel(ymd1, ymd2, search040204, seqList);

        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> wHouseMap = codeToName.wHouse();

        for(Tb413 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb413_1> tb413_1s = entity.getTb413_1s();
            String prt = "인쇄전";

            String shipNo = entity.getShipDt().substring(0,4) + "-" + entity.getShipDt().substring(4,6) + "-" + entity.getShipDt().substring(6,8) + "-" + entity.getShipNo();

            String goodsNm = goodsMap.get(tb413_1s.get(0).getGoodsCd());
            String custNm = custMap.get(entity.getCustCd());
            String whouseNm = wHouseMap.get(entity.getWhouseCd());

            if(!entity.getPrtEmp().isEmpty() && !entity.getPrtDt().isEmpty()) prt = "인쇄함";
            goods = goodsNm;
            for(Tb413_1 tb413_1 : tb413_1s){
                if(tb413_1s.size() > 1){
                    if(num + 1 == tb413_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                qty += tb413_1.getQty();
            }

            List<String> list = new ArrayList<>();
            list.add(shipNo);
            list.add(whouseNm);
            list.add(custNm);
            list.add(goods);
            list.add(String.valueOf(qty));
            list.add(entity.getName());
            list.add(entity.getZipCd());
            list.add(entity.getAddress2());
            list.add(entity.getAddress21());
            list.add(entity.getNote());
            list.add(prt);

            resultList.add(list);

            qty = 0;
            num = 0;

            seq = entity.getSeq();
        }

        return resultList;
    }

    @Override
    public Tb413Dto findById(Integer id) {
        Tb413 entity = tb413Repository.findById(id).orElseThrow(RuntimeException::new);
        Map<String, String> custMap = codeToName.cust();
        String custNm = custMap.get(entity.getCustCd());
        return Tb413Dto.of(entity, custNm);
    }

    @Override
    public List<Tb413_1Dto> findChildByFk(Integer id) {
        List<Tb413_1Dto> resultList = new ArrayList<>();
        List<Tb413_1> result = tb413_1Repository.findByFk(id);
        Map<String, String> goodsMap = codeToName.goods();
        result.forEach(e -> {
            String goodsNm = goodsMap.get(e.getGoodsCd());
            Tb413_1Dto dto = Tb413_1Dto.of(e, goodsNm);
            resultList.add(dto);
        });
        return resultList;
    }

    @Override
    public Long findShipNo() {
        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Long result = tb413Repository.findShipNo(nowDate);
        return result + 1;
    }

    @Transactional
    @Override
    public Boolean update(Tb413Form tb413Form, List<Tb413_1Form> tb413_1Form) {
        Tb413 entity = tb413Repository.findById(tb413Form.getSeq()).orElseThrow(RuntimeException::new);
        List<Tb413_1> tb413_1s = tb413_1Form.stream().filter(e -> !e.getGoodsCd().isEmpty()).map(e -> Tb413_1Form.toTb413_1(e, entity)).collect(Collectors.toList());
        entity.updateData(tb413Form, tb413_1s);
        return true;
    }

    @Transactional
    @Override
    public Boolean delete(Integer id) {
        tb413_1Repository.deleteByFk(id);
        tb413Repository.deleteById(id);
        return true;
    }
}
