package com.alex.wx.wechat;

import com.alex.wx.AbstractGenericController;
import com.alex.wx.core.order.beans.Order;
import com.alex.wx.core.order.beans.OrderProjection;
import com.alex.wx.core.wallet.beans.Wallet;
import com.alex.wx.wechat.service.WxHomeService;
import com.querydsl.core.types.Predicate;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("wx")
public class WxHomeController extends AbstractGenericController {

    private WxHomeService wxHomeService;

    public WxHomeController(WxHomeService customerService) {
        this.wxHomeService = customerService;
    }

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
        this.wxHomeService.commitOrder(params);
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
        this.wxHomeService.bindingAli(params);
        return Collections.singletonMap("success", "binding ali pay success.");
    }

    /**
     * 订单查询
     */
    @GetMapping("orders")
    public Object orders(@QuerydslPredicate(root = Order.class) Predicate predicate,
                         Pageable pageable,
                         PagedResourcesAssembler<OrderProjection> assembler) {

        Page<Order> bitCoins = this.wxHomeService.findByOrderPage(predicate, pageable);

        Link link = linkTo(methodOn(WxHomeController.class).orders(predicate,
                pageable, assembler)).withSelfRel();

        Page<OrderProjection> projected = bitCoins.map(data ->
                this.factory.createProjection(OrderProjection.class, data));

        logger.debug("market callable get end");

        return ResponseEntity.ok(assembler.toResource(projected, link));

    }

}
