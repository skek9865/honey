package project.honey.business.service.basic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import project.honey.business.dto.search.Search405;
import project.honey.business.dto.search.SearchPopUp405;
import project.honey.business.dto.basic.Tb405Dto;
import project.honey.business.entity.basic.Tb405;
import project.honey.business.form.basic.Tb405Form;
import project.honey.business.repository.basic.Tb405Repository;
import project.honey.comm.CodeToName;
import project.honey.comm.UploadService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class Service040105Impl implements Service040105{

    private final UploadService uploadService;
    private final Tb405Repository tb405Repository;
    private final CodeToName codeToName;

    @Override
    @Transactional
    public Boolean insert(Tb405Form form) throws IOException {
        String outNm = form.getImg().getOriginalFilename();
        String saveNm = null;

        int pos = outNm.lastIndexOf(".");
        String ext = outNm.substring(pos + 1);


        Tb405 entity = Tb405Form.toTb405(form, saveNm, outNm);
        Tb405 saveEntity = tb405Repository.save(entity);
        if(!form.getImg().isEmpty()){
            uploadService.uploadFile(form.getImg(), "C:/study/spring/team/honey/src/main/resources/static/images/patent", saveEntity.getSeq());
            entity.changeImgNmSave(saveEntity.getSeq(), ext);
        }

        return true;
    }

    @Override
    public Page<Tb405Dto> findAll(Search405 search, Pageable pageable) {
        Map<String, String> classMap = codeToName.commonCode("07");
        Map<String, String> productMap = codeToName.product();
        Map<String, String> itemGbMap = codeToName.itemGb();
        return tb405Repository.findAllByDsl(search,pageable).map(m -> Tb405Dto.of(m, classMap, productMap, itemGbMap));
    }

    @Override
    public Tb405Dto findById(Integer id) {
        Map<String, String> classMap = codeToName.commonCode("07");
        Map<String, String> productMap = codeToName.product();
        Map<String, String> itemGbMap = codeToName.itemGb();
        Tb405 entity = tb405Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb405Dto.of(entity, classMap, productMap, itemGbMap);
    }

    @Override
    @Transactional
    public Boolean update(Tb405Form form) {
        Tb405 entity = tb405Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        String outNm = StringUtils.hasText(form.getImg().getOriginalFilename())
                ? form.getImg().getOriginalFilename()
                : entity.getImgNmOut();

        int pos = outNm.lastIndexOf(".");
        String ext = outNm.substring(pos + 1);

        if(!form.getImg().isEmpty()){
            try {
                uploadService.deleteFile("C:/study/spring/team/honey/src/main/resources/static/images/patent",
                        entity.getImgNmSave(), entity.getSeq());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            outNm = uploadService.uploadFile(form.getImg(), "C:/study/spring/team/honey/src/main/resources/static/images/patent",
                    entity.getSeq());
        }

        entity.updateData(form, outNm);
        entity.changeImgNmSave(entity.getSeq(), ext);
        return true;
    }

    @Override
    @Transactional
    public Boolean delete(Integer id) {
        Tb405 entity = tb405Repository.findById(id).orElseThrow(RuntimeException::new);
        tb405Repository.deleteById(id);
        uploadService.deleteFile("C:/study/spring/team/honey/src/main/resources/static/files/patent", entity.getImgNmSave(), id);
        return true;
    }

    @Override
    public List<List<String>> findAllByExcel(Search405 search) {
        Map<String, String> classMap = codeToName.commonCode("07");
        Map<String, String> productMap = codeToName.product();
        Map<String, String> itemGbMap = codeToName.itemGb();
        List<Tb405> tb405s = tb405Repository.findAllByExcel(search);

        int stockQty = 0;
        int aQty = 0;
        int wPrice = 0;
        int fPrice = 0;
        List<List<String>> dtoList = new ArrayList<>();
        for (Tb405 tb405 : tb405s) {
            List<String> list = new ArrayList<>();
            list.add(tb405.getGoodsCd());
            list.add(tb405.getGoodsNm());
            list.add(tb405.getStandard());
            list.add(tb405.getUnit());
            list.add(classMap.get(tb405.getClassSeq()));
            list.add(tb405.getStockYn());
            list.add(tb405.getSetYn());
            list.add(productMap.get(tb405.getProduct()));
            list.add(itemGbMap.get(tb405.getItemGb1()));
            list.add(itemGbMap.get(tb405.getItemGb2()));
            list.add(tb405.getStockQty() != null ? String.valueOf(tb405.getStockQty()) : "0");
            list.add(tb405.getAQty() != null ? String.valueOf(tb405.getAQty()) : "0");
            list.add(tb405.getWPrice() != null ? String.valueOf(tb405.getWPrice()) : "0");
            list.add(tb405.getWPriceVat());
            list.add(tb405.getFPrice() != null ? String.valueOf(tb405.getFPrice()) : "0");
            list.add(tb405.getFPriceVat());
            list.add(tb405.getImgNmSave());
            list.add(tb405.getImgNmOut());
            dtoList.add(list);
            stockQty += tb405.getStockQty() != null ? tb405.getStockQty() : 0;
            aQty += tb405.getAQty() != null ? tb405.getAQty() : 0;
            wPrice += tb405.getWPrice() != null ? tb405.getWPrice() : 0;
            fPrice += tb405.getFPrice() != null ? tb405.getFPrice() : 0;
        }
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("총계");
        list.add(String.valueOf(stockQty));
        list.add(String.valueOf(aQty));
        list.add(String.valueOf(wPrice));
        list.add("");
        list.add(String.valueOf(fPrice));
        list.add("");
        list.add("");
        list.add("");
        dtoList.add(list);
        return dtoList;
    }

    @Override
    public List<Tb405Dto> findAllByPopUp(SearchPopUp405 search) {
        Map<String, String> classMap = codeToName.commonCode("07");
        Map<String, String> productMap = codeToName.product();
        Map<String, String> itemGbMap = codeToName.itemGb();

        return tb405Repository.findAllByPopUp(search).stream()
                .map(m -> Tb405Dto.of(m, classMap, productMap, itemGbMap))
                .collect(Collectors.toList());
    }

}
