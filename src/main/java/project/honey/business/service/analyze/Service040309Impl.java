package project.honey.business.service.analyze;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.honey.business.dto.analaze.Dto040309;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.entity.manage.Tb412_1;
import project.honey.business.form.analyze.Search040309;
import project.honey.business.repository.manage.Tb412Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.util.*;

@Service
@RequiredArgsConstructor
public class Service040309Impl implements Service040309{

    private final Tb412Repository tb412Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto040309> findAllByDsl(Search040309 search040309, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040309.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040309.getSYmd2(), "-");

        int amt = 0, vat = 0, tot = 0;

        Page<Tb412> result = tb412Repository.findAllBy040309(ymd1, ymd2, search040309.getSVatYn(), pageable);

        List<Dto040309> resultList = new ArrayList<>();

        Map<String, String> custMap = codeToName.cust();

        for(Tb412 entity : result.getContent()){
            List<Tb412_1> tb412_1s = entity.getTb412_1s();

            String custNm = custMap.get(entity.getCustCd());

            for(Tb412_1 tb412_1 : tb412_1s){
                amt += tb412_1.getAmt();
                vat += tb412_1.getVat();
            }
            tot = amt + vat;
            Dto040309 dto = Dto040309.of(entity, custNm, amt , vat, tot);
            resultList.add(dto);

            amt = 0;
            vat = 0;
            tot = 0;
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040309 search040309) {
        String ymd1 = GlobalMethod.replaceYmd(search040309.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040309.getSYmd2(), "-");

        int seq = 0, amt = 0, vat = 0, tot = 0;

        List<List<String>> resultList = new ArrayList<>();

        List<Tb412> result = tb412Repository.findAllBy040309Excel(ymd1, ymd2, search040309.getSVatYn());

        Map<String, String> custMap = codeToName.cust();

        for(Tb412 entity : result){
            if(seq == entity.getSeq()) continue;
            List<Tb412_1> tb412_1s = entity.getTb412_1s();

            String custNm = custMap.get(entity.getCustCd());
            String saleNo = entity.getSaleDt().substring(0,4) + "-" + entity.getSaleDt().substring(4,6) + "-" + entity.getSaleDt().substring(6,8) + "-" + entity.getSaleNo();

            for(Tb412_1 tb412_1 : tb412_1s){
                amt += tb412_1.getAmt();
                vat += tb412_1.getVat();
            }
            tot = amt + vat;

            List<String> list = new ArrayList<>();
            list.add(saleNo);
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
