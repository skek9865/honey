package project.honey.business.service.sale;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.honey.business.dto.sale.Dto040403;
import project.honey.business.entity.sale.Tb416;
import project.honey.business.form.analyze.Search040307;
import project.honey.business.repository.basic.Tb402Repository;
import project.honey.business.repository.sale.Tb416Repository;
import project.honey.business.repository.sale.Tb417Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Service040403Impl implements Service040403{

    private final Tb416Repository tb416Repository;
    private final Tb402Repository tb402Repository;
    private final Tb417Repository tb417Repository;
    private final CodeToName codeToName;

    @Override
    public List<Dto040403> findAllByDsl(Search040307 search040307) {
        String ymd1 = GlobalMethod.replaceYmd(search040307.getSYmd1(),"-");
        String ymd2 = GlobalMethod.replaceYmd(search040307.getSYmd2(),"-");

        int c1 = 0;
        Integer getPrice = 0, outPrice = 0, totPrice = 0;
        Integer empGetPrice = 0, empOutPrice = 0, empTotPrice = 0;

        List<Dto040403> resultList = new ArrayList<>();

        List<String> custList = tb402Repository.findAllByClass(search040307.getSCustGr());

        String closeYn = "Y";
        if(search040307.getSCloseYn() != null && search040307.getSCloseYn()) closeYn = "N";

        List<String> shipList = tb402Repository.findAllByShipYn(closeYn);

        List<Tb416> result = tb416Repository.findAllBy040307(ymd1, ymd2, search040307, custList, shipList);

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> custMap = codeToName.cust();

        for (Tb416 entity : result) {
            c1++;
            if(c1 == result.size()) {
                String empNm = empMap.get(entity.getEmpNo());
                String custNm = custMap.get(entity.getCustCd());

                getPrice = tb416Repository.sumAmtVat(entity.getCustCd(), entity.getSeq());
                outPrice = tb417Repository.sumOutAmountByCust(entity.getCustCd());
                if(getPrice == null) getPrice = 0;
                if(outPrice == null) outPrice = 0;
                totPrice = outPrice - getPrice;

                Dto040403 dto = Dto040403.of(entity, empNm, custNm, getPrice, outPrice, totPrice);
                resultList.add(dto);

                Dto040403 empDto = Dto040403.builder()
                        .empNm("")
                        .custNm("담당자 소계")
                        .getPrice(empGetPrice + getPrice)
                        .outPrice(empOutPrice + outPrice)
                        .totPrice(empTotPrice + totPrice)
                        .build();

                resultList.add(empDto);

                break;
            }
            if(entity.getCustCd().equals(result.get(c1).getCustCd()) && entity.getEmpNo().equals(result.get(c1).getEmpNo())) continue;

            String empNm = empMap.get(entity.getEmpNo());
            String custNm = custMap.get(entity.getCustCd());

            getPrice = tb416Repository.sumAmtVat(entity.getCustCd(), entity.getSeq());
            outPrice = tb417Repository.sumOutAmountByCust(entity.getCustCd());
            if(getPrice == null) getPrice = 0;
            if(outPrice == null) outPrice = 0;
            totPrice = outPrice - getPrice;

            if(search040307.getSCloseYn() != null && !search040307.getSZeroYn() && totPrice == 0) {
                getPrice = 0;
                outPrice = 0;
                totPrice = 0;
                continue;
            }

            Dto040403 dto = Dto040403.of(entity, empNm, custNm, getPrice, outPrice, totPrice);
            resultList.add(dto);

            empGetPrice += getPrice;
            empOutPrice += outPrice;
            empTotPrice += totPrice;

            if(!entity.getEmpNo().equals(result.get(c1).getEmpNo())){
                Dto040403 empDto = Dto040403.builder()
                        .empNm("")
                        .custNm("담당자 소계")
                        .getPrice(empGetPrice)
                        .outPrice(empOutPrice)
                        .totPrice(empTotPrice)
                        .build();

                resultList.add(empDto);

                empGetPrice = 0;
                empOutPrice = 0;
                empTotPrice = 0;
            }

            getPrice = 0;
            outPrice = 0;
            totPrice = 0;
        }

        return resultList;
    }

    @Override
    public List<List<String>> findAllByExcel(Search040307 search040307) {
        String ymd1 = GlobalMethod.replaceYmd(search040307.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040307.getSYmd2(), "-");

        int c1 = 0;
        Integer getPrice = 0, outPrice = 0, totPrice = 0;
        Integer empGetPrice = 0, empOutPrice = 0, empTotPrice = 0;

        List<String> custList = tb402Repository.findAllByClass(search040307.getSCustGr());

        String closeYn = "Y";
        if(search040307.getSCloseYn() != null && search040307.getSCloseYn()) closeYn = "N";

        List<String> shipList = tb402Repository.findAllByShipYn(closeYn);

        List<Tb416> result = tb416Repository.findAllBy040307(ymd1, ymd2, search040307, custList, shipList);

        Map<String, String> empMap = codeToName.emp();
        Map<String, String> custMap = codeToName.cust();

        List<List<String>> resultList = new ArrayList<>();

        for (Tb416 entity : result) {
            c1++;
            if(c1 == result.size()) {
                String empNm = empMap.get(entity.getEmpNo());
                String custNm = custMap.get(entity.getCustCd());

                getPrice = tb416Repository.sumAmtVat(entity.getCustCd(), entity.getSeq());
                outPrice = tb417Repository.sumOutAmountByCust(entity.getCustCd());
                if(getPrice == null) getPrice = 0;
                if(outPrice == null) outPrice = 0;
                totPrice = outPrice - getPrice;

                List<String> list = new ArrayList<>();
                list.add(empNm);
                list.add(custNm);
                list.add(String.valueOf(getPrice));
                list.add(String.valueOf(outPrice));
                list.add(String.valueOf(totPrice));

                resultList.add(list);

                List<String> list1 = new ArrayList<>();
                list1.add("");
                list1.add("담당자 소계");
                list1.add(String.valueOf(empGetPrice + getPrice));
                list1.add(String.valueOf(empOutPrice + outPrice));
                list1.add(String.valueOf(empTotPrice + totPrice));

                resultList.add(list1);

                break;
            }
            if(entity.getCustCd().equals(result.get(c1).getCustCd()) && entity.getEmpNo().equals(result.get(c1).getEmpNo())) continue;

            String empNm = empMap.get(entity.getEmpNo());
            String custNm = custMap.get(entity.getCustCd());

            getPrice = tb416Repository.sumAmtVat(entity.getCustCd(), entity.getSeq());
            outPrice = tb417Repository.sumOutAmountByCust(entity.getCustCd());
            if(getPrice == null) getPrice = 0;
            if(outPrice == null) outPrice = 0;
            totPrice = outPrice - getPrice;

            if(search040307.getSCloseYn() != null && !search040307.getSZeroYn() && totPrice == 0) {
                getPrice = 0;
                outPrice = 0;
                totPrice = 0;
                continue;
            }

            List<String> list3 = new ArrayList<>();
            list3.add(empNm);
            list3.add(custNm);
            list3.add(String.valueOf(getPrice));
            list3.add(String.valueOf(outPrice));
            list3.add(String.valueOf(totPrice));

            resultList.add(list3);

            empGetPrice += getPrice;
            empOutPrice += outPrice;
            empTotPrice += totPrice;

            if(!entity.getEmpNo().equals(result.get(c1).getEmpNo())){
                List<String> list4 = new ArrayList<>();
                list4.add("");
                list4.add("담당자 소계");
                list4.add(String.valueOf(empGetPrice));
                list4.add(String.valueOf(empOutPrice));
                list4.add(String.valueOf(empTotPrice));

                resultList.add(list4);

                empGetPrice = 0;
                empOutPrice = 0;
                empTotPrice = 0;
            }

            getPrice = 0;
            outPrice = 0;
            totPrice = 0;
        }

        return resultList;
    }
}
