package project.honey.business.service.manage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.manage.Tb410Dto;
import project.honey.business.dto.manage.Tb410MainDto;
import project.honey.business.dto.manage.Tb410_1Dto;
import project.honey.business.entity.manage.Tb410;
import project.honey.business.entity.manage.Tb410_1;
import project.honey.business.form.manage.Search040201;
import project.honey.business.form.manage.Tb410Form;
import project.honey.business.form.manage.Tb410_1Form;
import project.honey.business.repository.manage.Tb410Repository;
import project.honey.business.repository.manage.Tb410_1Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;
import project.honey.system.dto.CodeDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class Service040201Impl implements Service040201{

    private final Tb410Repository tb410Repository;
    private final Tb410_1Repository tb410_1Repository;
    private final CodeToName codeToName;

    @Transactional
    @Override
    public Boolean insert(Tb410Form tb410Form, List<Tb410_1Form> tb410_1Form) {
        Tb410 tb410 = Tb410Form.toTb410(tb410Form);
        Tb410 fk = tb410Repository.save(tb410);
        tb410_1Form.forEach(e -> {
            if(!e.getGoodsCd().isEmpty()) {
                Tb410_1 tb410_1 = Tb410_1Form.toTb410_1(e, fk.getSeq());
                tb410_1Repository.save(tb410_1);
            }
        });
        return true;
    }

    @Override
    public Page<Tb410MainDto> findAllByDsl(Search040201 search040201, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040201.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040201.getSYmd2(), "-");

        int price = 0, num = 0;
        String est, goods = null;

        List<Tb410_1> findSeqList = tb410_1Repository.findSeqGoods(search040201.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb410().getSeq()));
        set.forEach(seqList::add);

        Page<Tb410> result = tb410Repository.findAllByDsl(ymd1, ymd2, search040201, seqList, pageable);

        result.forEach(e -> log.info("findResult = {}", e));

        List<Tb410MainDto> resultList = new ArrayList<>();

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();

        for(Tb410 entity : result.getContent()){
            List<Tb410_1> tb410_1s = entity.getTb410_1s();
            String prt = "인쇄전";

            String estDt = entity.getEstimDt();
            String fdt = estDt.substring(0,4);
            String sdt = estDt.substring(4,6);
            String tdt = estDt.substring(6,8);

            est = fdt + "-" + sdt + "-" + tdt + "-" + entity.getEstimNo();

            String goodsNm = goodsMap.get(tb410_1s.get(0).getGoodsCd());
            String empNm = empMap.get(entity.getEmpNo());
            String statusNm = statusMap.get(entity.getStatus());
            String custNm = custMap.get(entity.getCustCd());

            if(entity.getPrtEmp() != null && entity.getPrtDt() != null) prt = "인쇄함";
            for(Tb410_1 tb410_1 : tb410_1s){
                goods = goodsNm;
                if(tb410_1s.size() > 1){
                    if(num + 1 == tb410_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                price += tb410_1.getAmt();
                price += tb410_1.getVat();
            }
            Tb410MainDto dto = Tb410MainDto.of(entity, est, goods, price, empNm, statusNm, prt, custNm);
            resultList.add(dto);
            num = 0;
            price = 0;
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040201 search040201) {
        String ymd1 = GlobalMethod.replaceYmd(search040201.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040201.getSYmd2(), "-");

        int price = 0, num = 0, seq = 0;
        String est, goods = null;

        List<Tb410_1> findSeqList = tb410_1Repository.findSeqGoods(search040201.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb410().getSeq()));
        set.forEach(seqList::add);

        List<List<String>> resultList = new ArrayList<>();

        List<Tb410> result = tb410Repository.findAllByExcel(ymd1, ymd2, search040201, seqList);

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();

        for(Tb410 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb410_1> tb410_1s = entity.getTb410_1s();
            log.info("entity = {}", entity);
            log.info("tb_410_1 = {}", tb410_1s);
            String prt = "인쇄전";

            String estDt = entity.getEstimDt();
            String fdt = estDt.substring(0,4);
            String sdt = estDt.substring(4,6);
            String tdt = estDt.substring(6,8);

            est = fdt + "-" + sdt + "-" + tdt + "-" + entity.getEstimNo();

            String goodsNm = goodsMap.get(tb410_1s.get(0).getGoodsCd());
            String empNm = empMap.get(entity.getEmpNo());
            String statusNm = statusMap.get(entity.getStatus());
            String custNm = custMap.get(entity.getCustCd());

            if(!entity.getPrtEmp().isEmpty() && !entity.getPrtDt().isEmpty()) prt = "인쇄함";
            goods = goodsNm;
            for(Tb410_1 tb410_1 : tb410_1s){
                if(tb410_1s.size() > 1){
                    if(num + 1 == tb410_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                price += tb410_1.getAmt();
                price += tb410_1.getVat();
            }

            List<String> list = new ArrayList<>();
            list.add(est);
            list.add(custNm);
            list.add(empNm);
            list.add(goods);
            list.add(entity.getExpDt());
            list.add(String.valueOf(price));
            list.add(statusNm);
            list.add(prt);
            list.add(entity.getNote());

            resultList.add(list);

            price = 0;
            num = 0;

            seq = entity.getSeq();
        }

        return resultList;
    }

    @Override
    public Tb410Dto findById(Integer id) {
        Tb410 entity = tb410Repository.findById(id).orElseThrow(RuntimeException::new);
        Map<String, String> custMap = codeToName.cust();
        String custNm = custMap.get(entity.getCustCd());
        return Tb410Dto.of(entity, custNm);
    }

    @Override
    public List<Tb410_1Dto> findChildByFk(Integer id) {
        List<Tb410_1Dto> resultList = new ArrayList<>();
        List<Tb410_1> result = tb410_1Repository.findByFk(id);
        Map<String, String> goodsMap = codeToName.goods();
        result.forEach(e -> {
            String goodsNm = goodsMap.get(e.getGoodsCd());
            Tb410_1Dto dto = Tb410_1Dto.of(e, goodsNm);
            resultList.add(dto);
        });
        return resultList;
    }

    @Override
    public Long findEstNo() {
        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Long result = tb410Repository.findEstNo(nowDate);
        return result + 1;
    }

    @Transactional
    @Override
    public Boolean update(Tb410Form tb410Form, List<Tb410_1Form> tb410_1Form) {
        Tb410 entity = tb410Repository.findById(tb410Form.getSeqP()).orElseThrow(RuntimeException::new);
        List<Tb410_1> tb410_1s = tb410_1Form.stream().filter(e -> !e.getGoodsCd().isEmpty()).map(e -> Tb410_1Form.toTb410_1(e, entity.getSeq())).collect(Collectors.toList());
        entity.updateData(tb410Form, tb410_1s);
        return true;
    }

    @Transactional
    @Override
    public Boolean delete(Integer id) {
        tb410_1Repository.deleteByFk(id);
        tb410Repository.deleteById(id);
        return true;
    }

    @Override
    public List<CodeDto> findAllBySelect(Integer type) {
        return null;
    }
}
