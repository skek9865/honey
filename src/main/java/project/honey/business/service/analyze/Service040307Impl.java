//package project.honey.business.service.analyze;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Service;
//import project.honey.business.dto.analaze.Dto040307;
//import project.honey.business.entity.basic.Tb402;
//import project.honey.business.entity.manage.Tb412;
//import project.honey.business.entity.manage.Tb412_1;
//import project.honey.business.form.analyze.Search040307;
//import project.honey.business.repository.basic.Tb402Repository;
//import project.honey.business.repository.manage.Tb412Repository;
//import project.honey.business.repository.manage.Tb412_1Repository;
//import project.honey.comm.CodeToName;
//import project.honey.comm.GlobalMethod;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class Service040307Impl implements Service040307{
//
//    private final Tb412Repository tb412Repository;
//    private final Tb412_1Repository tb412_1Repository;
//    private final Tb402Repository tb402Repository;
//    private final CodeToName codeToName;
//
//    @Override
//    public List<Dto040307> findAllByDsl(Search040307 search040307) {
//        String ymd1 = GlobalMethod.replaceYmd(search040307.getSYmd1(),"-");
//        String ymd2 = GlobalMethod.replaceYmd(search040307.getSYmd2(),"-");
//
//        Integer salePrice = 0, getPrice = 0, totPrice = 0;
//
//        List<String> custList = tb402Repository.findAllByClass(search040307.getSCustGr());
//        List<Tb402> tb402 = tb402Repository.findAll();
//
//        List<Tb412> result = tb412Repository.findAllBy040307(ymd1, ymd2, search040307, custList);
//        String empNo = result.get(0).getEmpNo(), custCd = result.get(0).getCustCd();
//
//        for (Tb412 entity : result) {
//            List<Tb412_1> tb412_1s = entity.getTb412_1s();
//            for (Tb412_1 tb412_1 : tb412_1s) {
//                if(custCd.equals(entity.getCustCd())){
//                    salePrice +=
//                }
//                entity.getCustCd()
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public List<List<String>> findAllByExcel(Search040307 search040307) {
//        return null;
//    }
//}
