package com.alex.wechat.config;


import com.alex.wechat.handler.*;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpConfiguration {

    private WxMpProperties properties;
    private ShareBtnEventMessageHandler shareBtnEventMessageHandler;
    private TbPwdMessageHandler tbPwdMessageHandler;
    private SubscribeMessageHandler subscribeMessageHandler;
    private RobotRetaBtnEventHandler robotRetaBtnEventHandler;
    private ShopBtnEventHandler shopBtnEventHandler;
    private KefuEventMessageHandler kefuEventMessageHandler;

    @Autowired
    public WxMpConfiguration(WxMpProperties properties,
                             ShareBtnEventMessageHandler shareBtnEventMessageHandler,
                             TbPwdMessageHandler tbPwdMessageHandler,
                             SubscribeMessageHandler subscribeMessageHandler,
                             RobotRetaBtnEventHandler robotRetaBtnEventHandler,
                             ShopBtnEventHandler shopBtnEventHandler,
                             KefuEventMessageHandler kefuEventMessageHandler) {

        this.properties = properties;
        this.shareBtnEventMessageHandler = shareBtnEventMessageHandler;
        this.tbPwdMessageHandler = tbPwdMessageHandler;
        this.subscribeMessageHandler = subscribeMessageHandler;
        this.robotRetaBtnEventHandler = robotRetaBtnEventHandler;
        this.shopBtnEventHandler = shopBtnEventHandler;
        this.kefuEventMessageHandler = kefuEventMessageHandler;
    }


    @Bean
    @Primary
    public WxMpService wxMpService() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(this.properties.getAppId());
        configStorage.setSecret(this.properties.getSecret());
        configStorage.setToken(this.properties.getToken());
        configStorage.setAesKey(this.properties.getAesKey());
        WxMpService service = new WxMpServiceImpl();
        service.setWxMpConfigStorage(configStorage);
        return service;
    }

    /**
     * 这是微信消息路由配置
     */
    @Bean
    @Primary
    public WxMpMessageRouter wxMpMessageRouter(WxMpService wxMpService) {

        WxMpMessageRouter wxMpMessageRouter = new WxMpMessageRouter(wxMpService);

        // 关注事件
        wxMpMessageRouter.rule()
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE)
                .handler(this.subscribeMessageHandler)
                .end();

        //匹配用户口令文字消息
        wxMpMessageRouter.rule()
                .msgType(WxConsts.XmlMsgType.TEXT)
                .async(false)
                .rContent("^[@客服].*")
                .handler(this.kefuEventMessageHandler)
                .end();

        //匹配用户口令文字消息
        wxMpMessageRouter.rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.TEXT)
                .handler(this.tbPwdMessageHandler)
                .end();

        // 秦客优果店铺
        wxMpMessageRouter.rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.CLICK)
                .eventKey("B1003_SHOP")
                .handler(this.shopBtnEventHandler)
                .end();

        // 女王节专属会场
        wxMpMessageRouter.rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.CLICK)
                .eventKey("B1002_RATE")
                .handler(this.robotRetaBtnEventHandler)
                .end();

        //匹配用户点击获取二维码
        wxMpMessageRouter.rule()
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.CLICK)
                .eventKey("B1001_SHARE")
                .async(false)
                .handler(this.shareBtnEventMessageHandler)
                .next();

        return wxMpMessageRouter;
    }
}
