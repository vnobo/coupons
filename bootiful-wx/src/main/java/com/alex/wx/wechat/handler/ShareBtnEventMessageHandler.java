package com.alex.wx.wechat.handler;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alex.wx.core.Customer.beans.Customer;
import com.fasterxml.jackson.databind.JsonNode;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.util.Map;

@Component
public class ShareBtnEventMessageHandler extends AbstractMessageHandler {


    @Override
    public WxMpXmlOutMessage customizeHandle(WxMpXmlMessage wxMessage,
                                             Map<String, Object> context,
                                             WxMpService wxMpService, WxSessionManager sessionManager) {
        try {
            // 为用户发送提醒
            sendKefuMessage(wxMpService, wxMessage);

            Customer customer = this.customerService.loadByOpenId(wxMessage.getFromUser());
            JsonNode ticketNode = customer.getExtend().findValue("ticket");
            File file;
            if (ObjectUtil.isNull(ticketNode)) {
                this.logger.info("ticket Str is null,create ticket. ");
                WxMpQrCodeTicket ticket = wxMpService.getQrcodeService().qrCodeCreateLastTicket(wxMessage.getFromUser());
                // 保存申请的二维码
                customer.getExtend().putPOJO("ticket", ticket);
                //this.customerService.asyncSave(customer);
                file = wxMpService.getQrcodeService().qrCodePicture(ticket);
            } else {
                this.logger.info("ticketStr is " + ticketNode.toString());
                WxMpQrCodeTicket ticket = WxMpQrCodeTicket.fromJson(ticketNode.toString());
                file = wxMpService.getQrcodeService().qrCodePicture(ticket);
            }

            Image source = ImageIO.read(new ClassPathResource("static/zhaomu.jpg").getInputStream());

            Image qrcode = ImageIO.read(file);
            qrcode = ImgUtil.scale(qrcode, 208, 208);
            Image image = ImgUtil.pressImage(source, qrcode, 104 + 45, 104 + 75, 1);

            URL url = new URL(customer.getHeadImgUrl());
            Image avater = ImageIO.read(url);
            avater = ImgUtil.scale(avater, 102, 102);
            image = ImgUtil.pressImage(image, avater, 33 - 320 + 51, 51 + 380, 1);

            BufferedImage canvasTxt = new BufferedImage(425, 50, BufferedImage.TYPE_INT_BGR);
            Graphics2D g2d = canvasTxt.createGraphics();
            g2d.setBackground(new Color(248, 232, 121));
            g2d.clearRect(0, 0, 425, 50);

            Font font = new Font("宋体", Font.BOLD + Font.ITALIC, 46);
            // 抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.BLACK);
            g2d.setFont(font);
            // 透明度
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1));
            // 在指定坐标绘制水印文字
            final FontMetrics metrics = g2d.getFontMetrics(font);
            final int textHeight = metrics.getAscent() - metrics.getLeading() - metrics.getDescent();
            g2d.drawString(customer.getNickname(), 2, Math.abs(50 + textHeight) / 2);
            g2d.dispose();

            image = ImgUtil.pressImage(image, canvasTxt, 210 - 320 + 213, 50 + 370, 1);

            if (file.canWrite()) {
                this.logger.info("file can write {}",file.canWrite());
            }

            if (ImageIO.write((RenderedImage) image, "jpg", file)) {

                WxMediaUploadResult res = wxMpService.getMaterialService().
                        mediaUpload(WxConsts.MaterialType.IMAGE, file);
                customer.getExtend().put("posterMediaId", res.getMediaId());

                this.logger.info("media id is {}", res.getMediaId());

                //this.customerService.asyncSave(customer);

                return WxMpXmlOutMessage.IMAGE()
                        .fromUser(wxMessage.getToUser())
                        .toUser(wxMessage.getFromUser())
                        .mediaId(res.getMediaId())
                        .build();
            }

            return null;

        } catch (WxErrorException | IOException e) {

            this.logger.error("创建微信二维码错误,错误信息 " + e.getMessage());

            return WxMpXmlOutMessage.TEXT()
                    .content("获取二维码失败. /撇嘴")
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser())
                    .build();
        }

    }

    /**
     * 主动发送消息,告诉二维码提示消息.
     */
    private void sendKefuMessage(WxMpService wxMpService, WxMpXmlMessage wxMessage) throws WxErrorException {

        String content = "亲,正在生成你的专属二维码...\n" +
                "=======注意========\n" +
                "1. 好友扫描你的二维码关注秦客优果,使用Alex购物.\n" +
                "2. 成功购买一笔商品(确认收货后),你都得到收益现金奖励.🎁\n";

        WxMpKefuMessage message = WxMpKefuMessage.TEXT()
                .content(content).toUser(wxMessage.getFromUser()).build();

        // 设置消息的内容等信息
        wxMpService.getKefuService().sendKefuMessage(message);
    }
}
