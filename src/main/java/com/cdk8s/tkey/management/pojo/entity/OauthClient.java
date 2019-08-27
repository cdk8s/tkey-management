package com.cdk8s.tkey.management.pojo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Table(name = "oauth_client")
public class OauthClient {
	/**
	 * 主键ID
	 */
	@Id
	@Column(name = "id")
	private Long id;

	/**
	 * 账号名称
	 */
	@Column(name = "client_name")
	private String clientName;

	/**
	 * 账号ID
	 */
	@Column(name = "client_id")
	private String clientId;

	/**
	 * 账号密钥
	 */
	@Column(name = "client_secret")
	private String clientSecret;

	/**
	 * 账号匹配的网站，支持正则符号
	 */
	@Column(name = "client_url")
	private String clientUrl;

	/**
	 * 账号描述
	 */
	@Column(name = "client_desc")
	private String clientDesc;

	/**
	 * logo 的链接地址
	 */
	@Column(name = "logo_url")
	private String logoUrl;

	/**
	 * 排序，默认值100，值越小越靠前(rank是保留字)
	 */
	@Column(name = "ranking")
	private Integer ranking;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 是否启动, 1正常，2禁用
	 */
	@Column(name = "state_enum")
	private Integer stateEnum;

	/**
	 * 是否删除, 1正常，2删除
	 */
	@Column(name = "delete_enum")
	private Integer deleteEnum;

	/**
	 * 创建时间
	 */
	@Column(name = "create_date")
	private Long createDate;

	/**
	 * 创建人
	 */
	@Column(name = "create_user_id")
	private Long createUserId;

	/**
	 * 更新时间
	 */
	@Column(name = "update_date")
	private Long updateDate;

	/**
	 * 更新人
	 */
	@Column(name = "update_user_id")
	private Long updateUserId;

	/**
	 * 删除时间
	 */
	@Column(name = "delete_date")
	private Long deleteDate;

	/**
	 * 删除人
	 */
	@Column(name = "delete_user_id")
	private Long deleteUserId;
}
