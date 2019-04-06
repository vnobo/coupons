package com.alex.core;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author billb
 */
@RestController
@RequestMapping("security")
public class SecurityController {

    @GetMapping("/userinfo")
    public @ResponseBody User userinfo(@RequestParam Map map) {
        return new User();
    }
}
