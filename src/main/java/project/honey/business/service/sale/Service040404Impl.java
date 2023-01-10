package project.honey.business.service.sale;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.honey.business.dto.sale.Dto040404;
import project.honey.business.entity.sale.Tb417;
import project.honey.business.form.sale.Search040404;
import project.honey.business.repository.sale.Tb417Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Service040404Impl implements Service040404{

    private final Tb417Repository tb417Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto040404> findAllByDsl(Search040404 search040404, Pageable pageable) {
        String ymd1 = GlobalMethod.replaceYmd(search040404.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040404.getSYmd2(), "-");

        List<Dto040404> resultList = new ArrayList<>();

        Map<String, String> custMap = codeToName.cust();

        Page<Tb417> result = tb417Repository.findAllBy040404(ymd1, ymd2, search040404, pageable);

        for (Tb417 entity : result.getContent()) {
            String custNm = custMap.get(entity.getCustCd());
            Dto040404 dto = Dto040404.of(entity, custNm);
            resultList.add(dto);
        }

        return new PageImpl<>(resultList, pageable, result.getTotalElements());
    }

    @Override
    public List<List<String>> findAllByExcel(Search040404 search040404) {
        String ymd1 = GlobalMethod.replaceYmd(search040404.getSYmd1(), "-");
        String ymd2 = GlobalMethod.replaceYmd(search040404.getSYmd2(), "-");

        List<List<String>> resultList = new ArrayList<>();

        Map<String, String> custMap = codeToName.cust();

        List<Tb417> result = tb417Repository.findAllBy040404Excel(ymd1, ymd2, search040404);

        for (Tb417 entity : result) {
            String custNm = custMap.get(entity.getCustCd());
            String outDt = entity.getAmountDt().substring(0,4) + "-" + entity.getAmountDt().substring(4,6) + "-" + entity.getAmountDt().substring(6,8) + "-" + entity.getAmountNo();

            List<String> list = new ArrayList<>();
            list.add(outDt);
            list.add(custNm);
            list.add(String.valueOf(entity.getAmount()));
            list.add(entity.getNote());

            resultList.add(list);
        }

        return resultList;
    }
}
