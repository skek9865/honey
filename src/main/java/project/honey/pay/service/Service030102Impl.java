package project.honey.pay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.pay.dto.Tb301Dto;
import project.honey.pay.dto.Tb302Dto;
import project.honey.pay.dto.Tb302PopupDto;
import project.honey.pay.dto.Tb302HomeDto;
import project.honey.pay.entity.Tb301;
import project.honey.pay.entity.Tb302;
import project.honey.pay.repository.Tb301Repository;
import project.honey.pay.repository.Tb302Repository;
import project.honey.personDepart.entity.Tb201;
import project.honey.personDepart.repository.Tb201Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Service030102Impl implements Service030102 {

    private final Tb201Repository tb201Repository;
    private final Tb302Repository tb302Repository;
    private final Tb301Repository tb301Repository;

    @Override
    @Transactional
    public Integer insert(Tb302Dto dto) {
        Tb301 tb301 = tb301Repository.findByItemCd(dto.getItemCd()).orElseThrow(RuntimeException::new);
        Tb302 tb302 = Tb302.builder()
                .empNo(dto.getEmpNo())
                .itemDiv(tb301.getItemDiv())
                .taxDiv(tb301.getTaxDiv())
                .itemCd(tb301.getItemCd())
                .payAmt(dto.getPayAmt().doubleValue())
                .build();

        return tb302Repository.save(tb302).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb302Dto dto) {
        Tb302 tb302 = tb302Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        tb302.changeInfo(dto);

        return tb302.getSeq();
    }

    @Override
    public List<Tb302PopupDto> findAll(String empNo) {
        // ?????? ??????
        Tb201 tb201 = tb201Repository.findByEmpNo(empNo).orElseThrow(RuntimeException::new);

        // ????????? ???????????? ??????
        List<Tb302> list = tb302Repository.findAllByEmpNoOrderByItemCdAsc(empNo);

        log.info("list.size() = {}", list.size());

        // ?????? ??????
        List<Tb302PopupDto> dtos = new ArrayList<>();
        int price = 0;
        for (Tb302 tb302 : list) {
            if (tb302.getItemDiv().equals("00001") && tb302.getTaxDiv().equals("Y")) {
                price += tb302.getPayAmt();
            }
        }

        // ????????? ????????? ????????? ??????, ?????? ???????????? QueryDsl??? ??????
        for (Tb302 tb302 : list) {
            Tb301 tb301 = tb301Repository.findByItemCd(tb302.getItemCd()).orElseThrow(RuntimeException::new);
            Tb302PopupDto dto = Tb302PopupDto.builder()
                    .seq(tb302.getSeq())
                    .empNo(tb302.getEmpNo())
                    .empNm(tb201.getEmpNm())
                    .post(tb201.getPost())
                    .itemDiv(tb302.getItemDiv())
                    .taxDiv(tb302.getTaxDiv())
                    .itemCd(tb302.getItemCd())
                    .taxRate(tb301.getTaxRate())
                    .build();

            if (tb302.getItemDiv().equals("00001")) {
                dto.setPayAmt(tb302.getPayAmt());
            }else{
                if (price == 0) {
                    dto.setPayAmt(0.0);
                } else {
                    if (tb301.getTaxRate() > 0) {
                        dto.setPayAmt(Math.floor(price * tb302.getPayAmt() / 100) * -1);
                    } else if (tb302.getPayAmt() != 0) {
                        dto.setPayAmt(tb302.getPayAmt() * -1);
                    } else {
                        dto.setPayAmt(tb302.getPayAmt());
                    }

                }
            }

            dtos.add(dto);
        }



        return dtos;
    }

    // ????????????????????? ????????? ??????, ???????????? ???????????? ??????
    @Override
    public Page<Tb302HomeDto> findAllByLeave(Pageable pageable, String empNm, String postCd, String deptCd) {

        Page<Tb201> result = tb201Repository.findAllByLeave(empNm, postCd, deptCd, pageable);

        List<Tb201> content = result.getContent();  // ?????? ???????????? ??????

        List<Tb302HomeDto> dtos = new ArrayList<>();
        for (Tb201 tb201 : content) {               // ??????????????? ????????? ??????
            int payout = 0; //?????????
            int taxAmt = 0; //?????????
            double deductionSub = 0.0;
            int deduction = 0;  //?????????
            int actualPayment = 0;  //????????????
            List<Tb302> list = tb302Repository.findAllByEmpNoOrderByItemCdAsc(tb201.getEmpNo());
            // ?????? ???????????? QueryDsl??? ??????
            for (Tb302 tb302 : list) {
                Tb301 tb301 = tb301Repository.findByItemCd(tb302.getItemCd()).orElseThrow(RuntimeException::new);
                Double payAmt = tb302.getPayAmt();

                if (tb302.getItemDiv().equals("00001")) { // ????????? ???
                    payout += payAmt;
                    if (tb302.getTaxDiv().equals("Y")) { // ??????????????? ????????? ???
                        taxAmt += payAmt;
                    }
                } else if (tb302.getItemDiv().equals("00002")) {    // ????????? ???
                    if(tb301.getTaxRate() > 0) deductionSub += payAmt;
                    else deduction += payAmt;
                }
            }
            // ????????? - ???????????? ?????? * ?????????
            deduction += (int) (Math.ceil((payout- (payout - taxAmt)) * deductionSub) / 100);
            actualPayment = payout - deduction;
            dtos.add(Tb302HomeDto.of(tb201, payout, taxAmt, deduction, actualPayment));
        }


        List<Tb302HomeDto> collect = dtos.stream().peek(dto -> {
            String empDt = dto.getEmpDt();
            dto.setEmpDt(empDt.substring(0, 4) + "-" + empDt.substring(4, 6) + "-" + empDt.substring(6));
        }).collect(Collectors.toList());

        return new PageImpl<>(collect, pageable, result.getTotalElements());
    }

    @Override
    public Tb302Dto findById(Integer seq) {
        return Tb302Dto.of(tb302Repository.findById(seq).orElseThrow(RuntimeException::new));
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb302Repository.deleteById(seq);
        return seq;
    }

    // ??????????????????(??????)
    @Override
    @Transactional
    public String pitemeSaveOne(String empNo) {
        // ?????? ????????? ??????
        if (tb302Repository.existsByEmpNo(empNo)) {
            return empNo;
        }

        log.info("empNo : {}", empNo);
        createTb302AndSave(empNo);
        return empNo;
    }

    // ??????????????????(??????)
    @Override
    @Transactional
    public String pitemeDelOne(String empNo) {
        tb302Repository.deleteAllByEmpNo(empNo);

        return empNo;
    }

    @Override
    @Transactional
    public String pitemeSave() {
        List<Tb201> tb201s = tb201Repository.findAll();
        for (Tb201 tb201 : tb201s) {
            if (tb302Repository.existsByEmpNo(tb201.getEmpNo())) continue;
            createTb302AndSave(tb201.getEmpNo());
        }

        return "ok";
    }

    private void createTb302AndSave(String empNo) {
        List<Tb301> tb301s = tb301Repository.findAll().stream()
                .filter(f -> f.getUseYn().equals("Y"))
                .collect(Collectors.toList());

        for (Tb301 tb301 : tb301s) {
            Tb302 tb302 = Tb302.builder()
                    .empNo(empNo)
                    .itemDiv(tb301.getItemDiv())
                    .taxDiv(tb301.getTaxDiv())
                    .itemCd(tb301.getItemCd())
                    .payAmt(tb301.getTaxRate())
                    .build();
            tb302Repository.save(tb302);
        }
    }

    @Override
    @Transactional
    public String pitemeDel() {
        tb302Repository.deleteAll();
        return "ok";
    }
}
