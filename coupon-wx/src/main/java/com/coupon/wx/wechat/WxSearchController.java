package com.coupon.wx.wechat;

import com.coupon.wx.BaseGenericController;
import com.coupon.wx.wechat.service.WxSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("wx/search")
@RequiredArgsConstructor
public class WxSearchController extends BaseGenericController {

    private final WxSearchService wxSearchService;

    @GetMapping("{openid}/{id}")
    public Object getDetail(@PathVariable String openid, @PathVariable String id) {
        return null;
    }

    @GetMapping("recommend/{id}")
    public Object getDetail(@PathVariable String id) {
        return null;
    }

    @GetMapping("all")
    public Object searchAll(@RequestParam Map<String, String> params) {

        Set<String> keys = params.keySet();
        keys.parallelStream().forEach(k -> {
            String value = params.get(k);
            if (StringUtils.isEmpty(value)) {
                params.remove(k, value);
            }
        });


        return null;
    }
}