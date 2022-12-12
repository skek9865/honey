package project.honey.comm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.honey.business.entity.basic.Tb402;
import project.honey.business.entity.basic.Tb404;
import project.honey.business.entity.basic.Tb405;
import project.honey.business.repository.basic.Tb402Repository;
import project.honey.business.repository.basic.Tb404Repository;
import project.honey.business.repository.basic.Tb405Repository;
import project.honey.pay.entity.Tb301;
import project.honey.pay.repository.Tb301Repository;
import project.honey.personDepart.entity.Tb201;
import project.honey.personDepart.entity.Tb202;
import project.honey.personDepart.repository.Tb201Repository;
import project.honey.personDepart.repository.Tb202Repository;
import project.honey.produce.entity.Tb501;
import project.honey.produce.repository.Tb501Repository;
import project.honey.system.dto.CodeDto;
import project.honey.system.entity.Tb904;
import project.honey.system.repository.Tb904Repository;
import project.honey.system.repository.Tb906Repository;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CodeToName {

    private final Tb201Repository tb201Repository;
    private final Tb202Repository tb202Repository;
    private final Tb301Repository tb301Repository;
    private final Tb402Repository tb402Repository;
    private final Tb404Repository tb404Repository;
    private final Tb405Repository tb405Repository;
    private final Tb501Repository tb501Repository;
    private final Tb904Repository tb904Repository;
    private final Tb906Repository tb906Repository;

    // 급여항목 변환
    public Map<String, String> item() {
        return tb301Repository.findAll().stream()
                .collect(Collectors.toMap(Tb301::getItemCd, Tb301::getItemNm));
    }

    // 부서명 변환
    public Map<String, String> dept() {
        return tb202Repository.findAll().stream()
                .collect(Collectors.toMap(Tb202::getDeptCd, Tb202::getDeptNm));
    }

    // 공통코드 변환
    public Map<String, String> commonCode(String fstId) {
        return tb906Repository.findByFstIdByDsl(fstId).stream()
                .collect(Collectors.toMap(CodeDto::getValue, CodeDto::getText));
    }


    // 화면아이디 -> 화면명 변환 tb903
    public Map<String, String> screen() {
        return tb904Repository.findAll().stream()
                .collect(Collectors.toMap(tb904 -> tb904.getFstId() + tb904.getScdId() + tb904.getThdId(), Tb904::getMenuNm));
    }

    // 사원 tb201
    public Map<String, String> emp() {
        return tb201Repository.findAll().stream()
                .collect(Collectors.toMap(Tb201::getEmpNo, Tb201::getEmpNm));
    }

    // 생산공정
    public Map<String, String> product() {
        return tb501Repository.findAll().stream()
                .collect(Collectors.toMap(Tb501::getProductCd, Tb501::getProductNm));
    }

    // 품목그룹
    public Map<String, String> itemGb() {
        return tb404Repository.findAll().stream()
                .collect(Collectors.toMap(Tb404::getClassCd, Tb404::getClassNm));
    }

    // 품목  tb405
    public Map<String, String> goods() {
        return tb405Repository.findAll().stream()
                .collect(Collectors.toMap(Tb405::getGoodsCd, Tb405::getGoodsNm));
    }

    // 거래처 tb402
    public Map<String, String> cust() {
        return tb402Repository.findAll().stream()
                .collect(Collectors.toMap(Tb402::getCustCd, Tb402::getCustNm));
    }
}
