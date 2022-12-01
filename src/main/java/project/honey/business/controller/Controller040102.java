package project.honey.business.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import project.honey.business.service.Service040102;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/040102")
public class Controller040102 {

    private final Service040102 service040102;
}
