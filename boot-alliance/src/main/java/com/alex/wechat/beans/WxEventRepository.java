package com.alex.wechat.beans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WxEventRepository extends JpaRepository<WxEvent, Long> {
}
