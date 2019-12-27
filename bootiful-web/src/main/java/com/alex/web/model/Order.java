package com.alex.web.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * rebate-alliance Orders
 * Created by 2019-02-25
 *
 * @author Alex bob(https://github.com/vnobo)
 */
@Data
@Table("orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    @Id
    private String id;
    private String openid;
    private Double price,payPrice;
    private LocalDateTime createdTime,updatedTime,syncTime;
    private JsonNode extend;
}
