package com.coupon.wx.wechat.service;

import com.coupon.wx.BaseGenericService;
import com.coupon.wx.wechat.beans.WxEventRepository;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.stereotype.Service;

/**
 * 处理微信通知事件服务
 */
@Service
public class WxEventService extends BaseGenericService {

    private WxEventRepository eventRepository;

    public void eventSaveAndUpdateUser(WxMpXmlMessage inMessage) {

        logger.debug("message manage and user update info.");

        /*asyncSave(WxEvent.of(inMessage.getMsgType(), objectMapper.convertValue(inMessage, ObjectNode.class)));*/

    }

    /*public WxEvent save(WxEvent event) {
        return eventRepository.save(event);
    }

    @Async
    public Future<WxEvent> asyncSave(WxEvent event) {
        return new AsyncResult<>(save(event));
    }
     */

}