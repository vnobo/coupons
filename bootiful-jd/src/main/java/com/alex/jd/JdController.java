package com.alex.wx.alliance.jd;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * rebate-alliance JdController
 * Created by 2019-02-26
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@RestController
@RequestMapping("jd")
public class JdController {

    private JdServer jdServer;

    public JdController(JdServer jdClient) {
        this.jdServer = jdClient;
    }

    @GetMapping("test")
    public Object test() {
        return jdServer.getSuperiorGoods(4, 1);
    }
}
