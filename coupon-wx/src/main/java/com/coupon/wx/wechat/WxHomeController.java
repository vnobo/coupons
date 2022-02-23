package com.coupon.wx.wechat;

import com.coupon.wx.BaseGenericController;
import com.coupon.wx.core.wallet.beans.Wallet;
import com.coupon.wx.wechat.service.WxHomeService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("wx")
@RequiredArgsConstructor
public class WxHomeController extends BaseGenericController {

    private final WxHomeService wxHomeService;


    @GetMapping(path = "index/{code}")
    public Object getUserByCode(@PathVariable String code) {

        try {
            return this.wxHomeService.loadByCode(code);
        } catch (WxErrorException e) {

            logger.error("get weixin user info error message: " + e.getMessage());
        }

        return Collections.singletonMap("error", "获取用户信息错误.");
    }

    @GetMapping(path = "info/{id}")
    public Object getInfoById(@PathVariable String id) {
        return this.wxHomeService.loadByOpenId(id);
    }

    @GetMapping(path = "jsconfig")
    public Object jsConfig(@RequestParam String url) {
        return this.wxHomeService.wxJsapi(url);
    }

    /**
     * 提交订单
     */
    @PostMapping("order-commit")
    public Object commitOrderId(@RequestBody Map<String, String> params) {
        //this.wxHomeService.commitOrder(params);
        return Collections.singletonMap("success", "commit model success.");
    }

    /**
     * 获取钱包个人钱包
     */
    @GetMapping("get-wallet/{openId}")
    public Object getWallet(@PathVariable String openId) {
        return this.wxHomeService.getWallet(openId);
    }

    /**
     * 提现
     */
    @PostMapping("withdraw")
    public Object withdraw(@RequestBody Wallet wallet) {
        return this.wxHomeService.withdraw(wallet.getOpenid());
    }

    /**
     * 绑定支付宝
     */
    @PostMapping("bind-ali")
    public Object bindingAli(@RequestBody Map<String, String> params) {
        //this.wxHomeService.bindingAli(params);
        return Collections.singletonMap("success", "binding ali pay success.");
    }


}