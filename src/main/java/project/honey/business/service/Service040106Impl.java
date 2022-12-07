package project.honey.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.honey.business.repository.Tb406Repository;
import project.honey.system.dto.CodeDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Service040106Impl implements Service040106{

    private final Tb406Repository tb406Repository;

    @Override
    public List<CodeDto> findAllBySelect() {
        List<CodeDto> result = tb406Repository.findAllBySelect();
        return result;
    }
}
