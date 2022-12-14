package project.honey.business.service.basic;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.business.dto.basic.Tb402Dto;
import project.honey.business.dto.basic.Tb402Popup;
import project.honey.business.entity.basic.Tb401;
import project.honey.business.entity.basic.Tb402;
import project.honey.business.entity.basic.Tb406;
import project.honey.business.form.basic.Tb402Form;
import project.honey.business.repository.basic.Tb401Repository;
import project.honey.business.repository.basic.Tb402Repository;
import project.honey.business.repository.basic.Tb406Repository;
import project.honey.personDepart.entity.Tb201;
import project.honey.personDepart.repository.Tb201Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Service040102Impl implements Service040102{

    private final Tb402Repository tb402Repository;
    private final Tb201Repository tb201Repository;
    private final Tb401Repository tb401Repository;
    private final Tb406Repository tb406Repository;

    @Override
    public Boolean insert(Tb402Form form) {
        Tb402 entity = Tb402Form.toTb402(form);
        tb402Repository.save(entity);
        return true;
    }

    @Override
    public Page<Tb402Dto> findAllByDsl(String custNm, String ceoNm, String empCd, String class1, Pageable pageable) {
        String empNm, class1Nm, class2Nm, saleGrNm, buyGrNm;
        List<Tb402Dto> dtoList = new ArrayList<>();

        List<Tb201> tb201 = tb201Repository.findAllByWorking();
        List<Tb401> tb401 = tb401Repository.findAll();
        List<Tb406> tb406 = tb406Repository.findAll();

        Page<Tb402> result = tb402Repository.findAllByDsl(custNm, ceoNm, empCd, class1, pageable);

        for(Tb402 entity : result.getContent()){

            empNm = makeEmpNm(entity, tb201);
            class1Nm = makeClass1Nm(entity, tb401);
            class2Nm = makeClass2Nm(entity, tb401);
            saleGrNm = makeSaleGrNm(entity, tb406);
            buyGrNm = makeBuyGrNm(entity, tb406);

            Tb402Dto tb402Dto = Tb402Dto.of(entity, empNm, class1Nm, class2Nm, saleGrNm, buyGrNm);
            dtoList.add(tb402Dto);
        }

        PageImpl<Tb402Dto> resultList = new PageImpl<>(dtoList, pageable, result.getTotalElements());
        return resultList;
    }

    @Override
    public List<List<String>> findAllByExcel(String custNm, String ceoNm, String empCd, String class1) {
        String empNm, class1Nm, class2Nm, saleGrNm, buyGrNm;
        List<List<String>> dtoList = new ArrayList<>();

        List<Tb201> tb201 = tb201Repository.findAllByWorking();
        List<Tb401> tb401 = tb401Repository.findAll();
        List<Tb406> tb406 = tb406Repository.findAll();

        List<Tb402> result = tb402Repository.findAllByExcel(custNm, ceoNm, empCd, class1);


        for(Tb402 entity : result){
            List<String> list = new ArrayList<>();

            empNm = makeEmpNm(entity, tb201);
            class1Nm = makeClass1Nm(entity, tb401);
            class2Nm = makeClass2Nm(entity, tb401);
            saleGrNm = makeSaleGrNm(entity, tb406);
            buyGrNm = makeBuyGrNm(entity, tb406);

            list.add(entity.getCustCd());
            list.add(entity.getCustNm());
            list.add(entity.getCeoNm());
            list.add(entity.getPhone());
            list.add(entity.getMobile());
            list.add(entity.getCustFax());
            list.add(empNm);
            list.add(entity.getSaleCd());
            list.add(class1Nm);
            list.add(class2Nm);
            list.add(saleGrNm);
            list.add(buyGrNm);

            dtoList.add(list);
        }

        return dtoList;
    }

    @Override
    public Tb402Dto findById(Integer id) {
        Tb402 entity = tb402Repository.findById(id).orElseThrow(RuntimeException::new);
        return Tb402Dto.of(entity, null, null, null, null, null);
    }

    @Transactional
    @Override
    public Boolean update(Tb402Form form) {
        Tb402 entity = tb402Repository.findById(form.getSeq()).orElseThrow(RuntimeException::new);
        entity.updateData(form);
        return true;
    }

    @Override
    public Boolean delete(Integer id) {
        tb402Repository.deleteById(id);
        return true;
    }

    @Override
    public List<Tb402Popup> findAllByPopup(String custNm) {
        return tb402Repository.findAllByPopup(custNm);
    }

    private String makeEmpNm(Tb402 entity, List<Tb201> tb201){
        String empNm;

        Optional<Tb201> find201 = tb201.stream().filter(e -> e.getEmpNo().equals(entity.getEmpCd())).findAny();
        if(find201.isPresent()) empNm = find201.get().getEmpNm();
        else empNm = "";

        return empNm;
    }

    private String makeClass1Nm(Tb402 entity, List<Tb401> tb401){
        String class1;

        Optional<Tb401> findClass1 = tb401.stream().filter(e -> e.getClassSeq().equals("00001") && e.getClassCd().equals(entity.getClass1())).findAny();
        if(findClass1.isPresent()) class1 = findClass1.get().getClassNm();
        else class1 = "";

        return class1;
    }

    private String makeClass2Nm(Tb402 entity, List<Tb401> tb401){
        String class2;

        Optional<Tb401> findClass2 = tb401.stream().filter(e -> e.getClassSeq().equals("00002") && e.getClassCd().equals(entity.getClass2())).findAny();
        if(findClass2.isPresent()) class2 = findClass2.get().getClassNm();
        else class2 = "";

        return class2;
    }

    private String makeSaleGrNm(Tb402 entity, List<Tb406> tb406){
        String saleGrNm;

        Optional<Tb406> findSaleGrNm = tb406.stream().filter(e -> e.getSpecialCd().equals(entity.getSaleGr())).findAny();
        if(findSaleGrNm.isPresent()) saleGrNm = findSaleGrNm.get().getSpecialNm();
        else saleGrNm = "";

        return saleGrNm;
    }

    private String makeBuyGrNm(Tb402 entity, List<Tb406> tb406){
        String buyGrNm;

        Optional<Tb406> findBuyGrNm = tb406.stream().filter(e -> e.getSpecialCd().equals(entity.getBuyGr())).findAny();
        if(findBuyGrNm.isPresent()) buyGrNm = findBuyGrNm.get().getSpecialNm();
        else buyGrNm = "";

        return buyGrNm;
    }
}
