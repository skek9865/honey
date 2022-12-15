package project.honey.company.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.comm.GlobalMethod;
import project.honey.company.dto.Tb105Dto;
import project.honey.company.entity.Tb105;
import project.honey.company.entity.Tb105_1;
import project.honey.company.repository.Tb105Repository;
import project.honey.company.repository.Tb105_1Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service010204Impl implements Service010204{

    private final Tb105Repository tb105Repository;
    private final Tb105_1Repository tb105_1Repository;
    private final Service010201 service010201;

    @Override
    @Transactional
    public Integer insert(Tb105Dto dto) {
        dto.beforeProcess();
        Tb105 tb105 = tb105Repository.save(dtoToEntity(dto));
        return tb105.getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb105Dto dto) {
        dto.beforeProcess();
        Tb105 tb105 = tb105Repository.findById(dto.getSeq())
                .orElseThrow(() -> new RuntimeException("해당 대출을 찾을 수 없습니다."));
        tb105.changeInfo(dto);
        return tb105.getSeq();
    }

    @Override
    public Page<Tb105Dto> findAll(Pageable pageable) {

        Page<Tb105> allByDsl = tb105Repository.findAllByDsl(pageable);
        List<Tb105Dto> dtoList = new ArrayList<>();
        for (Tb105 tb105 : allByDsl) {
            int totalInstamt = tb105.getTb105_1List().stream().mapToInt(Tb105_1::getInstamt).sum();
            Tb105Dto dto = entityToDto(tb105);
            dto.setInstamt(totalInstamt);
            dtoList.add(dto);
        }
        return new PageImpl<>(dtoList, pageable, allByDsl.getTotalElements());
    }

    @Override
    public Tb105Dto findById(Integer id) {
        Tb105 tb105 = tb105Repository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 대출을 찾을 수 없습니다."));
        return entityToDto(tb105);
    }

    @Override
    @Transactional
    public Integer delete(Integer id) {
        tb105Repository.deleteById(id);
        return id;
    }

    @Override
    public Integer getTotalLimitamt(List<Tb105Dto> dtoList) {
        return dtoList.stream().mapToInt(Tb105Dto::getLimitamt).sum();
    }

    @Override
    public Integer getTotalInstamt(List<Tb105Dto> dtoList) {
        return dtoList.stream().mapToInt(Tb105Dto::getInstamt).sum();
    }

    private Tb105Dto entityToDto(Tb105 entity){

        String newdt = GlobalMethod.makeYmd(entity.getNewdt(), "yyyy-MM-dd");
        String expdt = GlobalMethod.makeYmd(entity.getExpdt(), "yyyy-MM-dd");
        String ipaydt = GlobalMethod.makeYmd(entity.getIpaydt(), "yyyy-MM-dd");

        //통장pk -> 계좌번호로 변환
        Map<Integer, String> bank = service010201.bank();
        String bankNo = bank.get(entity.getFk_tb_102());

        return Tb105Dto.builder()
                .seq(entity.getSeq())
                .fk_tb_101(entity.getFk_tb_101())
                .loannm(entity.getLoannm())
                .fk_tb_102(bankNo)
                .newdt(newdt)
                .expdt(expdt)
                .limitamt(entity.getLimitamt())
                .ipaydt(ipaydt)
                .irate(entity.getIrate())
                .note(entity.getNote())
                .createId(entity.getCreateId())
                .createDate(entity.getCreateDate())
                .updateId(entity.getUpdateId())
                .updateDate(entity.getUpdateDate())
                .build();
    }

    private Tb105 dtoToEntity(Tb105Dto dto){

        return Tb105.builder()
                .seq(dto.getSeq())
                .fk_tb_101(dto.getFk_tb_101())
                .loannm(dto.getLoannm())
                .fk_tb_102(Integer.parseInt(dto.getFk_tb_102()))
                .newdt(dto.getNewdt())
                .expdt(dto.getExpdt())
                .limitamt(dto.getLimitamt())
                .ipaydt(dto.getIpaydt())
                .irate(dto.getIrate())
                .note(dto.getNote())
                .build();
    }

}
