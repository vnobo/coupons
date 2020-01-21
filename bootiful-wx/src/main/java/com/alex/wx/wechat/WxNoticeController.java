package com.alex.wx.wechat;

import com.alex.wx.BaseGenericController;
import com.alex.wx.wechat.service.WxEventService;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信分享页面
 */
@RestController
@RequestMapping("/weixin/notice/{appid}")
public class WxNoticeController extends BaseGenericController {

    @Autowired
    private WxEventService wxEventService;

    @Autowired
    private WxMpService wxService;

    @Autowired
    private WxMpMessageRouter wxMpMessageRouter;

    @GetMapping(produces = "text/plain;charset=utf-8")
    public String authGet(@PathVariable String appid,
                          @RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {

        this.logger.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature,
                timestamp, nonce, echostr);

        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new RequestException(500, "请求参数非法，请核实!");
        }

        if (wxService == null) {
            throw new RequestException(500, String.format("未找到对应appid=[%d]的配置，请核实！", appid));
        }

        if (wxService.checkSignature(timestamp, nonce, signature)) {
            logger.info("一个验证请求.");
            return echostr;
        }

        return "非法请求";
    }

    @PostMapping(produces = "application/xml; charset=UTF-8")
    public Object wxMessage(@PathVariable String appid,
                            @RequestBody String requestBody,
                            @RequestParam("signature") String signature,
                            @RequestParam("timestamp") String timestamp,
                            @RequestParam("nonce") String nonce,
                            @RequestParam("openid") String openid,
                            @RequestParam(name = "encrypt_type", required = false) String encType,
                            @RequestParam(name = "msg_signature", required = false) String msgSignature) {

        this.logger.info("\n 接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
                openid, signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!wxService.checkSignature(timestamp, nonce, signature)) {
            // 消息签名不正确，说明不是公众平台发过来的消息
            throw new RequestException(500, "非法请求，可能属于伪造的请求！");
        }


        String out = null;

        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);

            //保存消息存储
            wxEventService.eventSaveAndUpdateUser(inMessage);

            WxMpXmlOutMessage outMessage = this.wxMpMessageRouter.route(inMessage);

            if (outMessage == null) {
                //为null，说明路由配置有问题，需要注意
                logger.error("raw message router is null.");
                return "";
            }
            out = outMessage.toXml();

        } else if ("aes".equalsIgnoreCase(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxService.getWxMpConfigStorage(),
                    timestamp, nonce, msgSignature);

            this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());

            //保存消息存储
            wxEventService.eventSaveAndUpdateUser(inMessage);

            WxMpXmlOutMessage outMessage = this.wxMpMessageRouter.route(inMessage);

            if (outMessage == null) {
                //为null，说明路由配置有问题，需要注意
                logger.error("aes out Message router is null.");
                return "";
            }

            out = outMessage.toEncryptedXml(wxService.getWxMpConfigStorage());
        }

        this.logger.debug("\n组装回复信息：{}", out);

        return out;

    }


}
