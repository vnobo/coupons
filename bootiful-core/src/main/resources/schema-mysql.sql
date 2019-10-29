DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users`
(
    `username` VARCHAR(64) NOT NULL PRIMARY KEY COMMENT '登录用户',
    `password` VARCHAR(256) COMMENT '用户密码',
    `enabled`  CHAR(1) DEFAULT 0 COMMENT '是否启用'
);

CREATE TABLE authorities
(
    username  varchar(64) NOT NULL,
    authority varchar(64) NOT NULL,
    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
);
CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`
(
    `id`                    VARCHAR(64)  PRIMARY KEY COMMENT '订单号',
    `openid`                VARCHAR(64),
    `type`                  char(1)      NOT NULL DEFAULT 1 COMMENT '订单类型1,淘宝.2,京东.3,拼多多',
    `num_iid`               VARCHAR(64)  NOT NULL COMMENT '商品ID',
    `item_title`            varchar(128) COMMENT '商品名称',
    `item_num`              int                   DEFAULT 0 COMMENT '商品数量',
    `price`                 double                DEFAULT 0 COMMENT '单价',
    `pay_price`             double                DEFAULT 0 COMMENT '实际支付金额',
    `commission`            double                DEFAULT 0 COMMENT '推广者获得的收入金额，对应联盟后台报表“预估收入',
    `commission_rate`       double                DEFAULT 0 COMMENT '推广者获得的分成比率，对应联盟后台报表“分成比率',
    `create_time`           timestamp             DEFAULT current_timestamp COMMENT '淘客订单创建时间',
    `earning_time`          timestamp             DEFAULT current_timestamp COMMENT '淘客订单结算时间',
    `tk_status`             int                   DEFAULT 0 COMMENT '淘客订单状态，3：订单结算，12：订单付款， 13：订单失效，14：订单成功',
    `order_type`            VARCHAR(64) COMMENT '订单类型，如天猫，淘宝',
    `income_rate`           double                DEFAULT 0 COMMENT '收入比率，卖家设置佣金比率+平台补贴比率',
    `pub_share_pre_fee`     double                DEFAULT 0 COMMENT '效果预估，付款金额*(佣金比率+补贴比率)*分成比率',
    `subsidy_rate`          double                DEFAULT 0 COMMENT '	补贴比率',
    `adzone_id`             VARCHAR(64) COMMENT '广告位ID',
    `adzone_name`           VARCHAR(64) COMMENT '广告位名称',
    `alipay_total_price`    double                DEFAULT 0 COMMENT '付款金额',
    `total_commission_rate` double                DEFAULT 0 COMMENT '佣金比率',
    `total_commission_fee`  double                DEFAULT 0 COMMENT '佣金金额',
    `async_time`            timestamp             DEFAULT current_timestamp COMMENT '默认同步时间',
    `extend`                JSON COMMENT '账户扩展信息'
) COMMENT '返利订单';
