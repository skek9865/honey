package project.honey.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.repository.basic.Tb405Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;
import project.honey.stock.dto.Dto060111;
import project.honey.stock.dto.Dto060201;
import project.honey.stock.dto.search.Search060111;
import project.honey.stock.dto.search.Search060201;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service060201Impl implements Service060201{

    private final Tb405Repository tb405Repository;
    private final CodeToName codeToName;

    @Override
    public Page<Dto060201> findAll(Search060201 search, Pageable pageable) {

        Map<String, String> classMap = codeToName.commonCode("07");

        return tb405Repository.findAllByQuery(search, pageable)
                .map(m -> Dto060201.of(m, classMap));
    }

    @Override
    public List<List<String>> findAllByExcel(Search060201 search) {

        Map<String, String> classMap = codeToName.commonCode("07");

        List<Dto060201> dtoList = tb405Repository.findAllByQueryExcel(search).stream()
                .map(m -> Dto060201.of(m, classMap))
                .collect(Collectors.toList());

        List<List<String>> excelList = new ArrayList<>();

        int qty = 0;
        for (Dto060201 dto : dtoList) {
            List<String> list = new ArrayList<>();
            list.add(dto.getClassNm());
            list.add(dto.getGoodsNm());
            list.add(dto.getStandard());
            list.add(String.valueOf(dto.getQty()));

            excelList.add(list);
            qty += dto.getQty();
        }
        //총계
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("총계");
        list.add(String.valueOf(qty));
        excelList.add(list);

        return excelList;
    }
}
