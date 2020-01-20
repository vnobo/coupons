package com.alex.ali.core;

import com.alex.ali.core.service.TaoBaoServer;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * com.alex.taobao in  coupons
 *
 * @author Alex bob(https://github.com/vnobo)
 * @date Created by 2019/7/19
 */
@RestController
@RequestMapping("tb")
public class TaoBaoController {

    private final TaoBaoServer taoBaoServer;

    public TaoBaoController(TaoBaoServer taoBaoServer) {
        this.taoBaoServer = taoBaoServer;
    }

    @GetMapping("search")
    public Object superSearch(@RequestParam Map<String, Object> tbParams) {
        MultiValueMap<String,Object> params =new LinkedMultiValueMap<>();
        params.setAll(tbParams);
        return this.taoBaoServer.superSearch(params);
    }
}
