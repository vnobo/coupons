package com.alex.wx.wechat.beans;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;
import java.time.LocalDateTime;


@Data
public class WxEvent {

    private long id;
    private String event;
    private LocalDateTime createdTime;
    private ObjectNode extend;
}
