package project.honey.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.CodeToName;
import project.honey.comm.GlobalMethod;
import project.honey.company.dto.Tb104Dto;
import project.honey.company.entity.Tb104;
import project.honey.company.repository.Tb104Repository;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service010203Impl implements Service010203{

    private final Tb104Repository tb104Repository;
    private final Service010202 service010202;
    private final Service010201 service010201;
    private final CodeToName codeToName;

    @Override
    @Transactional
    public Integer insert(Tb104Dto dto) {
        dto.beforeProcess();
        Tb104 tb104 = tb104Repository.save(dtoToEntity(dto));
        return tb104.getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb104Dto dto) {
        dto.beforeProcess();
        Tb104 tb104 = tb104Repository.findById(dto.getSeq())
                .orElseThrow(() -> new IllegalArgumentException("해당 카드를 찾을 수 없습니다."));
        tb104.changInfo(dto);
        return tb104.getSeq();
    }

    @Override
    public Page<Tb104Dto> findAll(Pageable pageable) {
        Page<Tb104> entityList = tb104Repository.findAll(pageable);
        Page<Tb104Dto> dtoList = entityList.map(e -> entityToDto(e));
        return dtoList;
    }

    @Override
    public Tb104Dto findById(Integer id) {
        Tb104 tb104 = tb104Repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 카드를 찾을 수 없습니다."));
        return entityToDto(tb104);
    }

    @Override
    public Integer getTotalLimitamt() {
        return tb104Repository.getTotalLimitamt();
    }

    @Override
    @Transactional
    public Integer delete(Integer id) {
        tb104Repository.deleteById(id);
        return id;
    }

    private Tb104Dto entityToDto(Tb104 entity){
        String expdt = GlobalMethod.makeYmd(entity.getExpdt(), "yyyy-MM-dd");
        String issuedt = GlobalMethod.makeYmd(entity.getIssuedt(), "yyyy-MM-dd");

        //사원명 변환
        Map<String, String> emp = codeToName.emp();
        String empNm = emp.get(entity.getEmpno());

        //인증서명으로 변환
        Map<Integer, String> cer = service010202.cer();
        String cerNm = cer.get(entity.getFk_tb_103());

        //통장 계좌번호로 변환
        Map<Integer, String> bank = service010201.bank();
        String bankNo = bank.get(entity.getFk_tb_102());

        return Tb104Dto.builder()
                .seq(entity.getSeq())
                .fk_tb_101(entity.getFk_tb_101())
                .cardnm(entity.getCardnm())
                .cardno(entity.getCardno())
                .expdt(expdt)
                .cvcno(entity.getCvcno())
                .useyn(entity.getUseyn())
                .empno(empNm)
                .fk_tb_102(bankNo)
                .limitamt(entity.getLimitamt())
                .issuedt(issuedt)
                .fk_tb_103(cerNm)
                .note(entity.getNote())
                .createId(entity.getCreateId())
                .createDate(entity.getCreateDate())
                .updateId(entity.getUpdateId())
                .updateDate(entity.getUpdateDate())
                .build();
    }

    private Tb104 dtoToEntity(Tb104Dto dto){
        if(dto.getFk_tb_101()==null) dto.setFk_tb_101(27);
        return Tb104.builder()
                .seq(dto.getSeq())
                .fk_tb_101(dto.getFk_tb_101())
                .cardnm(dto.getCardnm())
                .cardno(dto.getCardno())
                .expdt(dto.getExpdt())
                .cvcno(dto.getCvcno())
                .useyn(dto.getUseyn())
                .empno(dto.getEmpno())
                .fk_tb_102(Integer.parseInt(dto.getFk_tb_102()))
                .limitamt(dto.getLimitamt())
                .issuedt(dto.getIssuedt())
                .fk_tb_103(Integer.parseInt(dto.getFk_tb_103()))
                .note(dto.getNote())
                .build();
    }
}
