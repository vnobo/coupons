package com.coupon.wx.wechat.handler;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KefuEventMessageHandler extends AbstractMessageHandler {

    @Override
    public WxMpXmlOutMessage customizeHandle(WxMpXmlMessage wxMessage,
                                             Map<String, Object> context,
                                             WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        this.logger.info("客服问题处理: {},用户: {}", wxMessage.getContent(), wxMessage.getFromUser());
        return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser()).build();
    }
}