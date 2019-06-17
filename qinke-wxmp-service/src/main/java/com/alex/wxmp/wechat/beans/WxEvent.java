package com.alex.wxmp.wechat.beans;

import com.alex.wxmp.ObjectNodeConverterJson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wx_events")
@EntityListeners(AuditingEntityListener.class)
public class WxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String event;

    @CreatedDate
    private LocalDateTime createdTime;

    @Convert(converter = ObjectNodeConverterJson.class)
    private ObjectNode extend;


    public static WxEvent of(String eventType, ObjectNode extend) {
        WxEvent event = new WxEvent();
        event.setEvent(eventType);
        event.setExtend(extend);
        return event;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public ObjectNode getExtend() {
        if (ObjectUtils.isEmpty(this.extend)) {
            this.extend = new ObjectMapper().createObjectNode();
        }
        return extend;
    }

    public void setExtend(ObjectNode extend) {
        this.extend = extend;
    }
}
