package project.honey.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.honey.business.repository.Tb409Repository;
import project.honey.system.dto.CodeDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Service040109Impl implements Service040109{

    private final Tb409Repository tb409Repository;

    @Override
    public List<CodeDto> findAllBySelect() {
        List<CodeDto> result = tb409Repository.findAllBySelect();
        return result;
    }
}
