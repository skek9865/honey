package project.honey.business.service.analyze;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.honey.business.dto.analaze.Dto040308;
import project.honey.business.entity.sale.Tb417;
import project.honey.business.repository.sale.Tb417Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Service040308Impl implements Service040308{

    private final Tb417Repository tb417Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto040308> findAllByDsl(String sYmd1, String sYmd2, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(sYmd1, "-");
        String ymd2 = GlobalMethod.replaceYmd(sYmd2, "-");

        List<Dto040308> resultList = new ArrayList<>();

        Map<String, String> custMap = codeToName.cust();

        List<Tb417> result = tb417Repository.findAllBy040308(ymd1, ymd2, pageable);
        Integer countResult = tb417Repository.count040308(ymd1, ymd2);
        result.forEach(e -> {
            String custNm = custMap.get(e.getCustCd());
            Dto040308 dto = Dto040308.of(e, custNm);
            resultList.add(dto);
        });

        return new PageImpl<>(resultList, pageable, countResult);
    }

    @Override
    public List<List<String>> findAllByExcel(String sYmd1, String sYmd2) {
        String ymd1 = GlobalMethod.replaceYmd(sYmd1, "-");
        String ymd2 = GlobalMethod.replaceYmd(sYmd2, "-");

        List<List<String>> resultList = new ArrayList<>();

        Map<String, String> custMap = codeToName.cust();

        List<Tb417> result = tb417Repository.findAllBy040308Excel(ymd1, ymd2);
        result.forEach(e -> {
            String amountNo = e.getAmountDt().substring(0,4) + "-" + e.getAmountDt().substring(4,6) + "-" + e.getAmountDt().substring(6,8) + "-" + e.getAmountNo();
            String custNm = custMap.get(e.getCustCd());

            List<String> list = new ArrayList<>();
            list.add(amountNo);
            list.add(custNm);
            list.add(String.valueOf(e.getPrice()));
            list.add(e.getNote());

            resultList.add(list);
        });

        return resultList;
    }
}
