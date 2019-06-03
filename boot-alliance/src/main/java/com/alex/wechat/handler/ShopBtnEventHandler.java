package com.alex.wechat.handler;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ShopBtnEventHandler extends AbstractMessageHandler {

    @Override
    public WxMpXmlOutMessage customizeHandle(WxMpXmlMessage wxMessage,
                                             Map<String, Object> context,
                                             WxMpService wxMpService, WxSessionManager sessionManager) {

        return WxMpXmlOutMessage.TEXT().content("女王节大牌上新，精选好货，大额券抵扣低至1折\n" +
                "【活动链接】https://m.tb.cn/h.3DDXo5R\n" +
                "-----------------\n" +
                "复制这条信息，￥kkYzbE7TVm3￥ ，打开【手机淘宝】即可查看").fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
    }

}
