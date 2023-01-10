package project.honey.business.service.sale;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.honey.business.dto.sale.Dto040405;
import project.honey.business.entity.sale.Tb416;
import project.honey.business.entity.sale.Tb416_1;
import project.honey.business.form.sale.Search040404;
import project.honey.business.repository.sale.Tb416Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.util.*;

@Service
@RequiredArgsConstructor
public class Service040405Impl implements Service040405{

    private final Tb416Repository tb416Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto040405> findAllByDsl(Search040404 search040404, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040404.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040404.getSYmd2(), "-");

        int amt = 0, vat = 0;

        Page<Tb416> result = tb416Repository.findAllBy040405(ymd1, ymd2, search040404, pageable);

        List<Dto040405> resultList = new ArrayList<>();

        Map<String, String> custMap = codeToName.cust();

        for(Tb416 entity : result.getContent()){
            List<Tb416_1> tb416_1s = entity.getTb416_1s();

            String custNm = custMap.get(entity.getCustCd());

            for(Tb416_1 tb416_1 : tb416_1s){
                amt += tb416_1.getAmt();
                vat += tb416_1.getVat();
            }
            Dto040405 dto = Dto040405.of(entity, custNm, amt, vat);
            resultList.add(dto);
            amt = 0;
            vat = 0;
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040404 search040404) {
        String ymd1 = GlobalMethod.replaceYmd(search040404.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040404.getSYmd2(), "-");

        int amt = 0, vat = 0, tot = 0, seq = 0;

        List<Tb416> result = tb416Repository.findAllBy040405Excel(ymd1, ymd2, search040404);

        List<List<String>> resultList = new ArrayList<>();

        Map<String, String> custMap = codeToName.cust();

        for(Tb416 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb416_1> tb416_1s = entity.getTb416_1s();

            String buyNo = entity.getBuyDt().substring(0,4) + "-" + entity.getBuyDt().substring(4,6) + "-" + entity.getBuyDt().substring(6,8) + "-" + entity.getBuyNo();

            String custNm = custMap.get(entity.getCustCd());

            for(Tb416_1 tb416_1 : tb416_1s){
                amt += tb416_1.getAmt();
                vat += tb416_1.getVat();
            }

            tot = amt + vat;

            List<String> list = new ArrayList<>();
            list.add(buyNo);
            list.add(custNm);
            list.add(String.valueOf(amt));
            list.add(String.valueOf(vat));
            list.add(String.valueOf(tot));

            resultList.add(list);

            amt = 0;
            vat = 0;
            tot = 0;

            seq = entity.getSeq();
        }

        return resultList;
    }
}
