package com.spring4all.isona.web;


import com.spring4all.isona.service.DiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private DiscoveryService discoveryService;

    @GetMapping("/")
    public String dashboard(ModelMap map) {
        // 暂时不设专门的dashboard，直接跳转到服务详情页面，待功能更多之后设置
        return "redirect://serviceList";
    }

}
