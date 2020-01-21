package com.alex.wx.wechat.service;

import com.alex.wx.BaseGenericService;
import com.alex.wx.wechat.beans.WxEvent;
import com.alex.wx.wechat.beans.WxEventRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

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
