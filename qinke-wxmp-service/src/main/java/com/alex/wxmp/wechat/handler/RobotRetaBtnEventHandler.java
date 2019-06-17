package com.alex.wxmp.wechat.handler;


import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RobotRetaBtnEventHandler extends AbstractMessageHandler {


    @Override
    public WxMpXmlOutMessage customizeHandle(WxMpXmlMessage wxMessage,
                                             Map<String, Object> context,
                                             WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

        String messageStr = "天猫女王节来啦！超多爆款新品上线，抢优惠券购物，大牌好货还可以更便宜！\n" +
                "【活动链接】https://m.tb.cn/h.3xc3yFh\n" +
                "-----------------\n" +
                "复制这条信息，￥6aVAbE6b1Rf￥ ，打开【手机淘宝】即可查看";
        return WxMpXmlOutMessage.TEXT().content(messageStr).fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
    }
}
