package biz.lwb.system.holder.controller;

import biz.lwb.system.holder.YamlObjectFactory;
import biz.lwb.system.holder.dto.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@RestController
@Slf4j
public class PersonStubController {

    @GetMapping(path = "address", produces = "application/json")
    public Set<Address> getServiceSpringInfoInjection(HttpServletRequest httpRequest) {
        return YamlObjectFactory.yamlObjectFactory("stubs").create("address", Set.class);
    }

}
