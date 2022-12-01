package project.honey.comm;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;
import project.honey.pay.entity.Tb301;
import project.honey.pay.repository.Tb301Repository;
import project.honey.personDepart.entity.Tb201;
import project.honey.personDepart.entity.Tb202;
import project.honey.personDepart.repository.Tb201Repository;
import project.honey.personDepart.repository.Tb202Repository;
import project.honey.system.dto.CodeDto;
import project.honey.system.entity.Tb904;
import project.honey.system.entity.Tb906;
import project.honey.system.repository.Tb904Repository;
import project.honey.system.repository.Tb906Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CodeToName {

    private final Tb201Repository tb201Repository;
    private final Tb202Repository tb202Repository;
    private final Tb301Repository tb301Repository;
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
}
