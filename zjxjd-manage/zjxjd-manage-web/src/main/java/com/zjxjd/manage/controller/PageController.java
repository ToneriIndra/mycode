package com.zjxjd.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 通用页面跳转
 * @author 赵加习
 *
 */
@RequestMapping("page")
@Controller
public class PageController {
    @RequestMapping(value = "{pageName}", method = RequestMethod.GET)
    public String toPage(@PathVariable("pageName") String pageName ) {
        return pageName;
    }
}
