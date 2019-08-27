DROP TABLE IF EXISTS `oauth_client`;

CREATE TABLE `oauth_client`
(
    `id`             bigint       NOT NULL COMMENT '主键ID',
    `client_name`    varchar(35)  NOT NULL COMMENT '账号名称',
    `client_id`      varchar(35)  NOT NULL COMMENT '账号ID',
    `client_secret`  varchar(35)  NOT NULL COMMENT '账号密钥',
    `client_url`     varchar(200) NOT NULL DEFAULT '' COMMENT '账号匹配的网站，支持正则符号',
    `client_desc`    varchar(50)  NULL COMMENT '账号描述',
    `logo_url`       varchar(200) NULL COMMENT 'logo 的链接地址',
    `ranking`        tinyint      NOT NULL DEFAULT '100' COMMENT '排序，默认值100，值越小越靠前(rank是保留字)',
    `remark`         varchar(255) NULL COMMENT '备注',
    `state_enum`     tinyint      NOT NULL DEFAULT '1' COMMENT '是否启动, 1正常，2禁用',
    `delete_enum`    tinyint      NOT NULL DEFAULT '1' COMMENT '是否删除, 1正常，2删除',
    `create_date`    bigint       NOT NULL COMMENT '创建时间',
    `create_user_id` bigint       NOT NULL COMMENT '创建人',
    `update_date`    bigint       NOT NULL COMMENT '更新时间',
    `update_user_id` bigint       NOT NULL COMMENT '更新人',
    `delete_date`    bigint       NULL COMMENT '删除时间',
    `delete_user_id` bigint       NULL COMMENT '删除人',
    PRIMARY KEY (`id`)
);
