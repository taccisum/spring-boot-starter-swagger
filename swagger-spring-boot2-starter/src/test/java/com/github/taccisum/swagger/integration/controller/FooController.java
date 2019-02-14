package com.github.taccisum.swagger.integration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tac - liaojf@cheegu.com
 * @since 2019/2/14
 */
@RestController
@RequestMapping("foo")
public class FooController {
    @GetMapping
    @RequestMapping("/")
    public String index() {
        return "hello";
    }
}
