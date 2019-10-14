package biz.lwb.system.holder.authentication.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/hello")
    public String firstPage() {
        return "Hello World";
    }

    @PreAuthorize("hasRole('ROLE_SUPERUSER')")
    @GetMapping("/superhello")
    public String anotherHello() {
        return "Super Hello World";
    }
}
