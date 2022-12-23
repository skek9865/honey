package project.honey.business.service.manage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.manage.*;
import project.honey.business.dto.search.SearchPopUp410;
import project.honey.business.entity.manage.Tb411;
import project.honey.business.entity.manage.Tb411_1;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.entity.manage.Tb412_1;
import project.honey.business.form.manage.*;
import project.honey.business.repository.manage.Tb412Repository;
import project.honey.business.repository.manage.Tb412_1Repository;
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
public class Service040203Impl implements Service040203{

    private final Tb412Repository tb412Repository;
    private final Tb412_1Repository tb412_1Repository;
    private final CodeToName codeToName;

    @Transactional
    @Override
    public Boolean insert(Tb412Form tb412Form, List<Tb412_1Form> tb412_1Form) {
        Tb412 tb412 = Tb412Form.toTb412(tb412Form);
        Tb412 fk = tb412Repository.save(tb412);
        tb412_1Form.forEach(e -> {
            if(!e.getGoodsCd().isEmpty()){
                Tb412_1 tb412_1 = Tb412_1Form.toTb412_1(e,fk);
                tb412_1Repository.save(tb412_1);
            }
        });
        return true;
    }

    @Override
    public Page<Tb412MainDto> findAllByDsl(Search040203 search040203, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040203.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040203.getSYmd2(), "-");

        int qty = 0, price = 0, num = 0;
        String order, goods = null;

        List<Tb412_1> findSeqList = tb412_1Repository.findSeqGoods(search040203.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb412().getSeq()));
        set.forEach(seqList::add);

        Page<Tb412> result = tb412Repository.findAllByDsl(ymd1, ymd2, search040203, seqList, pageable);

        List<Tb412MainDto> resultList = new ArrayList<>();

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> projectMap = codeToName.project();

        for(Tb412 entity : result.getContent()){
            List<Tb412_1> tb412_1s = entity.getTb412_1s();

            String goodsNm = goodsMap.get(tb412_1s.get(0).getGoodsCd());
            String empNm = empMap.get(entity.getEmpNo());
            String statusNm = statusMap.get(entity.getStatus());
            String custNm = custMap.get(entity.getCustCd());
            String projectNm = projectMap.get(entity.getProjectCd());

            for(Tb412_1 tb412_1 : tb412_1s){
                goods = goodsNm;
                if(tb412_1s.size() > 1){
                    if(num + 1 == tb412_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                price += tb412_1.getAmtSum();
                qty += tb412_1.getQty();
            }
            Tb412MainDto dto = Tb412MainDto.of(entity, custNm, empNm, goods , price, qty, projectNm, statusNm);
            resultList.add(dto);
            num = 0;
            price = 0;
            qty = 0;
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040203 search040203) {
        String ymd1 = GlobalMethod.replaceYmd(search040203.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040203.getSYmd2(), "-");

        int price = 0, num = 0, seq = 0, qty = 0;
        String order, goods = null;

        List<Tb412_1> findSeqList = tb412_1Repository.findSeqGoods(search040203.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb412().getSeq()));
        set.forEach(seqList::add);

        List<List<String>> resultList = new ArrayList<>();

        List<Tb412> result = tb412Repository.findAllByExcel(ymd1, ymd2, search040203, seqList);

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> statusMap = codeToName.commonCode("12");
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();
        Map<String, String> projectMap = codeToName.project();

        for(Tb412 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb412_1> tb412_1s = entity.getTb412_1s();
            String prt = "인쇄전";

            String saleNo = entity.getSaleDt().substring(0,4) + "-" + entity.getSaleDt().substring(4,6) + "-" + entity.getSaleDt().substring(6,8) + "-" + entity.getSaleNo();

            String goodsNm = goodsMap.get(tb412_1s.get(0).getGoodsCd());
            String empNm = empMap.get(entity.getEmpNo());
            String statusNm = statusMap.get(entity.getStatus());
            String custNm = custMap.get(entity.getCustCd());
            String projectNm = projectMap.get(entity.getProjectCd());

            if(!entity.getPrtEmp().isEmpty() && !entity.getPrtDt().isEmpty()) prt = "인쇄함";
            goods = goodsNm;
            for(Tb412_1 tb412_1 : tb412_1s){
                if(tb412_1s.size() > 1){
                    if(num + 1 == tb412_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                price += tb412_1.getAmtSum();
                qty += tb412_1.getQty();
            }

            List<String> list = new ArrayList<>();
            list.add(saleNo);
            list.add(custNm);
            list.add(empNm);
            list.add(goods);
            list.add(String.valueOf(price));
            list.add(String.valueOf(qty));
            list.add(entity.getTakeOk());
            list.add(projectNm);
            list.add(entity.getNote());
            list.add(entity.getName());
            list.add(entity.getZipCd());
            list.add(entity.getAddress());
            list.add(entity.getAddress1());
            list.add(entity.getNote2());
            list.add(statusNm);
            list.add(prt);

            resultList.add(list);

            price = 0;
            qty = 0;
            num = 0;

            seq = entity.getSeq();
        }

        return resultList;
    }

    @Override
    public Tb412Dto findById(Integer id) {
        Tb412 entity = tb412Repository.findById(id).orElseThrow(RuntimeException::new);
        Map<String, String> custMap = codeToName.cust();
        String custNm = custMap.get(entity.getCustCd());
        return Tb412Dto.of(entity, custNm);
    }

    @Override
    public List<Tb412_1Dto> findChildByFk(Integer id) {
        List<Tb412_1Dto> resultList = new ArrayList<>();
        List<Tb412_1> result = tb412_1Repository.findByFk(id);
        Map<String, String> goodsMap = codeToName.goods();
        result.forEach(e -> {
            String goodsNm = goodsMap.get(e.getGoodsCd());
            Tb412_1Dto dto = Tb412_1Dto.of(e, goodsNm);
            resultList.add(dto);
        });
        return resultList;
    }

    @Override
    public Long findSaleNo() {
        String nowDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        Long result = tb412Repository.findSaleNo(nowDate);
        return result + 1;
    }

    @Transactional
    @Override
    public Boolean update(Tb412Form tb412Form, List<Tb412_1Form> tb412_1Form) {
        Tb412 entity = tb412Repository.findById(tb412Form.getSeq()).orElseThrow(RuntimeException::new);
        List<Tb412_1> tb412_1s = tb412_1Form.stream().filter(e -> !e.getGoodsCd().isEmpty()).map(e -> Tb412_1Form.toTb412_1(e, entity)).collect(Collectors.toList());
        entity.updateData(tb412Form, tb412_1s);
        return true;
    }

    @Transactional
    @Override
    public Boolean delete(Integer id) {
        tb412_1Repository.deleteByFk(id);
        tb412Repository.deleteById(id);
        return true;
    }

    @Override
    public List<Tb412MainDto> findAllByPopUp(SearchPopUp410 searchPopUp410) {
        String ymd1 = GlobalMethod.replaceYmd(searchPopUp410.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(searchPopUp410.getSYmd2(), "-");

        int qty = 0, num = 0, seq = 0;
        String goods = null;

        List<Tb412_1> findSeqList = tb412_1Repository.findSeqGoods(searchPopUp410.getSGoodsCd());

        List<Integer> seqList = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        findSeqList.forEach(e -> set.add(e.getTb412().getSeq()));
        set.forEach(seqList::add);

        List<Tb412> result = tb412Repository.findAllByPopUp(ymd1, ymd2, searchPopUp410, seqList);

        List<Tb412MainDto> resultList = new ArrayList<>();

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> goodsMap = codeToName.goods();
        Map<String, String> custMap = codeToName.cust();

        for(Tb412 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb412_1> tb412_1s = entity.getTb412_1s();
            String prt = "인쇄전";

            String goodsNm = goodsMap.get(tb412_1s.get(0).getGoodsCd());
            String empNm = empMap.get(entity.getEmpNo());
            String custNm = custMap.get(entity.getCustCd());

            if(entity.getPrtEmp() != null && entity.getPrtDt() != null) prt = "인쇄함";
            for(Tb412_1 tb412_1 : tb412_1s){
                goods = goodsNm;
                if(tb412_1s.size() > 1){
                    if(num + 1 == tb412_1s.size()) goods = goodsNm + " 외" + num + "건";
                    num++;
                }
                qty += tb412_1.getQty();
            }
            Tb412MainDto dto = Tb412MainDto.of(entity, custNm, empNm, goods, null, qty, entity.getProjectCd(), entity.getStatus());
            resultList.add(dto);
            num = 0;
            qty = 0;

            seq = entity.getSeq();
        }

        return resultList;
    }
}
