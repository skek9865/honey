package project.honey.business.service.analyze;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.honey.business.dto.analaze.Dto040307;
import project.honey.business.entity.basic.Tb402;
import project.honey.business.entity.manage.Tb412;
import project.honey.business.form.analyze.Search040307;
import project.honey.business.repository.basic.Tb402Repository;
import project.honey.business.repository.manage.Tb412Repository;
import project.honey.business.repository.manage.Tb412_1Repository;
import project.honey.business.repository.sale.Tb417Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class Service040307Impl implements Service040307{

    private final Tb412Repository tb412Repository;
    private final Tb402Repository tb402Repository;
    private final Tb417Repository tb417Repository;
    private final CodeToName codeToName;

    @Override
    public List<Dto040307> findAllByDsl(Search040307 search040307) {
        String ymd1 = GlobalMethod.replaceYmd(search040307.getSYmd1(),"-");
        String ymd2 = GlobalMethod.replaceYmd(search040307.getSYmd2(),"-");

        int c1 = 0;
        Integer salePrice = 0, getPrice = 0, totPrice = 0;
        Integer empSalePrice = 0, empGetPrice = 0, empTotPrice = 0;

        List<Dto040307> resultList = new ArrayList<>();

        List<String> custList = tb402Repository.findAllByClass(search040307.getSCustGr());

        String closeYn = "Y";
        if(search040307.getSCloseYn() != null && search040307.getSCloseYn()) closeYn = "N";

        List<String> shipList = tb402Repository.findAllByShipYn(closeYn);

        List<Tb412> result = tb412Repository.findAllBy040307(ymd1, ymd2, search040307, custList, shipList);

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> custMap = codeToName.cust();

        for (Tb412 entity : result) {
            c1++;
            if(c1 == result.size()) {
                String empNm = empMap.get(entity.getEmpNo());
                String custNm = custMap.get(entity.getCustCd());

                salePrice = tb412Repository.sumAmtVat(entity.getCustCd(), entity.getSeq());
                getPrice = tb417Repository.sumAmountByCust(entity.getCustCd());
                if(salePrice == null) salePrice = 0;
                if(getPrice == null) getPrice = 0;
                totPrice = salePrice - getPrice;

                Dto040307 dto = Dto040307.of(entity, empNm, custNm, salePrice, getPrice, totPrice);
                resultList.add(dto);

                Dto040307 empDto = Dto040307.builder()
                        .empNm("")
                        .custCd("")
                        .custNm("담당자 소계")
                        .salePrice(empSalePrice + salePrice)
                        .getPrice(empGetPrice + getPrice)
                        .totPrice(empTotPrice + totPrice)
                        .build();

                resultList.add(empDto);

                break;
            }
            if(entity.getCustCd().equals(result.get(c1).getCustCd()) && entity.getEmpNo().equals(result.get(c1).getEmpNo())) continue;

            String empNm = empMap.get(entity.getEmpNo());
            String custNm = custMap.get(entity.getCustCd());

            salePrice = tb412Repository.sumAmtVat(entity.getCustCd(), entity.getSeq());
            getPrice = tb417Repository.sumAmountByCust(entity.getCustCd());
            if(salePrice == null) salePrice = 0;
            if(getPrice == null) getPrice = 0;
            log.info("getPrice = {}", getPrice);
            totPrice = salePrice - getPrice;

            if(search040307.getSCloseYn() != null && !search040307.getSZeroYn() && totPrice == 0) {
                salePrice = 0;
                getPrice = 0;
                totPrice = 0;
                continue;
            }

            Dto040307 dto = Dto040307.of(entity, empNm, custNm, salePrice, getPrice, totPrice);
            resultList.add(dto);

            empSalePrice += salePrice;
            empGetPrice += getPrice;
            empTotPrice += totPrice;

            if(!entity.getEmpNo().equals(result.get(c1).getEmpNo())){
                Dto040307 empDto = Dto040307.builder()
                        .empNm("")
                        .custCd("")
                        .custNm("담당자 소계")
                        .salePrice(empSalePrice)
                        .getPrice(empGetPrice)
                        .totPrice(empTotPrice)
                        .build();

                resultList.add(empDto);

                empSalePrice = 0;
                empGetPrice = 0;
                empTotPrice = 0;
            }

            salePrice = 0;
            getPrice = 0;
            totPrice = 0;
        }

        return resultList;
    }

    @Override
    public List<List<String>> findAllByExcel(Search040307 search040307) {
        String ymd1 = GlobalMethod.replaceYmd(search040307.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040307.getSYmd2(), "-");

        int c1 = 0;
        Integer salePrice = 0, getPrice = 0, totPrice = 0;
        Integer empSalePrice = 0, empGetPrice = 0, empTotPrice = 0;

        List<String> custList = tb402Repository.findAllByClass(search040307.getSCustGr());

        String closeYn = "Y";
        if(search040307.getSCloseYn() != null && search040307.getSCloseYn()) closeYn = "N";

        List<String> shipList = tb402Repository.findAllByShipYn(closeYn);

        List<Tb412> result = tb412Repository.findAllBy040307(ymd1, ymd2, search040307, custList, shipList);

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> custMap = codeToName.cust();

        List<List<String>> resultList = new ArrayList<>();

        for (Tb412 entity : result) {
            c1++;
            if(c1 == result.size()) {
                String empNm = empMap.get(entity.getEmpNo());
                String custNm = custMap.get(entity.getCustCd());

                salePrice = tb412Repository.sumAmtVat(entity.getCustCd(), entity.getSeq());
                getPrice = tb417Repository.sumAmountByCust(entity.getCustCd());
                if(salePrice == null) salePrice = 0;
                if(getPrice == null) getPrice = 0;
                totPrice = salePrice - getPrice;

                List<String> list = new ArrayList<>();
                list.add(empNm);
                list.add(entity.getCustCd());
                list.add(custNm);
                list.add(String.valueOf(salePrice));
                list.add(String.valueOf(getPrice));
                list.add(String.valueOf(totPrice));

                resultList.add(list);

                List<String> list1 = new ArrayList<>();
                list1.add("");
                list1.add("");
                list1.add("담당자 소계");
                list1.add(String.valueOf(empSalePrice + salePrice));
                list1.add(String.valueOf(empGetPrice + getPrice));
                list1.add(String.valueOf(empTotPrice + totPrice));

                resultList.add(list1);

                break;
            }
            if(entity.getCustCd().equals(result.get(c1).getCustCd()) && entity.getEmpNo().equals(result.get(c1).getEmpNo())) continue;

            String empNm = empMap.get(entity.getEmpNo());
            String custNm = custMap.get(entity.getCustCd());

            salePrice = tb412Repository.sumAmtVat(entity.getCustCd(), entity.getSeq());
            getPrice = tb417Repository.sumAmountByCust(entity.getCustCd());
            if(salePrice == null) salePrice = 0;
            if(getPrice == null) getPrice = 0;
            totPrice = salePrice - getPrice;

            if(search040307.getSCloseYn() != null && !search040307.getSZeroYn() && totPrice == 0) {
                salePrice = 0;
                getPrice = 0;
                totPrice = 0;
                continue;
            }

            List<String> list3 = new ArrayList<>();
            list3.add(empNm);
            list3.add(entity.getCustCd());
            list3.add(custNm);
            list3.add(String.valueOf(salePrice));
            list3.add(String.valueOf(getPrice));
            list3.add(String.valueOf(totPrice));

            resultList.add(list3);

            empSalePrice += salePrice;
            empGetPrice += getPrice;
            empTotPrice += totPrice;

            if(!entity.getEmpNo().equals(result.get(c1).getEmpNo())){
                List<String> list4 = new ArrayList<>();
                list4.add("");
                list4.add("");
                list4.add("담당자 소계");
                list4.add(String.valueOf(empSalePrice));
                list4.add(String.valueOf(empGetPrice));
                list4.add(String.valueOf(empTotPrice));

                resultList.add(list4);

                empSalePrice = 0;
                empGetPrice = 0;
                empTotPrice = 0;
            }

            salePrice = 0;
            getPrice = 0;
            totPrice = 0;
        }

        return resultList;
    }
}
