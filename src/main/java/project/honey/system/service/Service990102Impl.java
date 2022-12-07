package project.honey.system.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.honey.system.dto.Tb902Dto;
import project.honey.system.entity.Tb902;
import project.honey.system.repository.Tb902Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class Service990102Impl implements Service990102{

    private final Tb902Repository tb902Repository;
    private final HttpSession session;

    @Override
    @Transactional
    public Integer insert(Tb902Dto dto) {
        return tb902Repository.save(Tb902Dto.toTb902(dto)).getSeq();
    }

    @Override
    @Transactional
    public Integer update(Tb902Dto dto) {
        Tb902 tb902 = tb902Repository.findById(dto.getSeq()).orElseThrow(RuntimeException::new);
        tb902.changeInfo(dto);
        return dto.getSeq();
    }

    @Override
    public Page<Tb902Dto> findAll(Pageable pageable) {
        return tb902Repository.findAll(pageable).map(Tb902Dto::of);
    }



    @Override
    @Transactional
    public void insertHistory(HttpServletRequest request, String userId) {

        String timeStamp = String.valueOf(LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).toInstant().toEpochMilli())
                .substring(0,10);

        Tb902 tb902 = Tb902.builder()
                .userId(userId)
                .ipAddr(request.getRemoteAddr())
                .timeStamp(timeStamp)
                .remotePort(request.getRemotePort())
                .sessId(session.getId())
                .userAgent(request.getHeader("User-Agent"))
                .build();
        tb902Repository.save(tb902);
    }

    @Override
    public Tb902Dto findById(Integer id) {
        return Tb902Dto.of(tb902Repository.findById(id).orElseThrow(RuntimeException::new));
    }

    @Override
    @Transactional
    public Integer delete(Integer seq) {
        tb902Repository.deleteById(seq);
        return seq;
    }

    @Override
    public List<List<String>> findAllByExcel() {
        List<Tb902> tb902s = tb902Repository.findAll();
        List<List<String>> excelData = new ArrayList<>();

        for(Tb902 entity : tb902s){
            List<String> list = new ArrayList<>();
            list.add(entity.getUserId());
            list.add(entity.getTimeStamp());
            list.add(entity.getIpAddr());
            list.add(String.valueOf(entity.getRemotePort()));
            list.add(entity.getSessId());
            list.add(entity.getUserAgent());
            excelData.add(list);
        }
        return excelData;
    }
}
