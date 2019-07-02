DROP TABLE IF EXISTS `wx_customers`;
CREATE TABLE `wx_customers`
(
    `id`           int AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    `openid`       varchar(64) NOT NULL UNIQUE COMMENT '账户微信ID',
    `nickname`     varchar(64) COMMENT '微信昵称',
    `sex`          char(1)   DEFAULT 0 COMMENT '性别 1 男 2女',
    `city`         varchar(32) COMMENT '城市',
    `country`      varchar(32) COMMENT '国家',
    `province`     varchar(32) COMMENT '省份',
    `head_img_url` varchar(512) COMMENT '头像地址',
    `created_Time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `update_time`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最后改变日期',
    `async_Time`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '最后改变日期',
    `extend`       JSON COMMENT '账户主体'
);

/*********************淘宝PID 帮助区分用户*****************************/
DROP TABLE IF EXISTS `tb_pids`;
CREATE TABLE `tb_pids`
(
    `id`   int PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name` varchar(20)     NOT NULL,
    `pid`  varchar(36)     NOT NULL,
    `p3`   char(12)    DEFAULT NULL,
    `type` varchar(10) DEFAULT NULL,
    UNIQUE (`pid`)
);


DROP TABLE IF EXISTS `wx_events`;
CREATE TABLE `wx_events`
(
    `id`           bigint(20) UNSIGNED AUTO_INCREMENT PRIMARY KEY COMMENT '事件ID',
    `event`        varchar(64) NOT NULL COMMENT '事件类型',
    `created_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '事件日期',
    `extend`       json                 DEFAULT NULL COMMENT '事件主体'
);

DROP TABLE IF EXISTS `wallets`;
CREATE TABLE `wallets`
(
    `id`             int AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    `openid`         VARCHAR(64) NOT NULL UNIQUE,
    `promoter`       varchar(64) COMMENT '推广人',
    `aliname`        VARCHAR(64),
    `alipay`         VARCHAR(64),
    `balance`        DOUBLE      NOT NULL DEFAULT 0 COMMENT '账户余额',
    `total_balance`  DOUBLE      NOT NULL DEFAULT 0 COMMENT '累计金额',
    `rate`           DOUBLE      NOT NULL DEFAULT 0 COMMENT '返利比率',
    `brokerage`      DOUBLE      NOT NULL DEFAULT 0 COMMENT '推广佣金',
    `brokerage_rate` DOUBLE      NOT NULL DEFAULT 0 COMMENT '推广返利比率',
    `created_Time`   TIMESTAMP            DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `update_time`    TIMESTAMP            DEFAULT CURRENT_TIMESTAMP COMMENT '最后改变日期',
    `extend`         JSON COMMENT '账户扩展信息'
) COMMENT '账户额';

DROP TABLE IF EXISTS `withdraws`;
CREATE TABLE `withdraws`
(
    `id`           int AUTO_INCREMENT PRIMARY KEY COMMENT 'ID',
    `openid`       VARCHAR(64) NOT NULL COMMENT '提款人',
    `aliname`      VARCHAR(64),
    `alipay`       VARCHAR(64),
    `balance`      DOUBLE      NOT NULL DEFAULT 0 COMMENT '提款额',
    `status`       int         NOT NULL DEFAULT 0 COMMENT '提款状态,0 申请,50 审核,100 成功',
    `created_Time` TIMESTAMP            DEFAULT CURRENT_TIMESTAMP COMMENT '申请日期',
    `update_time`  TIMESTAMP            DEFAULT CURRENT_TIMESTAMP COMMENT '审核日期',
    `extend`       JSON COMMENT '账户扩展信息'
) COMMENT '账户余额提现表';


DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`
(
    `id`                    bigint AUTO_INCREMENT PRIMARY KEY,
    `openid`                VARCHAR(64),
    `trade_id`              VARCHAR(128) NOT NULL COMMENT '订单号',
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
    `extend`                JSON COMMENT '账户扩展信息',
    UNIQUE (`trade_id`, `type`)
) COMMENT '返利订单';
