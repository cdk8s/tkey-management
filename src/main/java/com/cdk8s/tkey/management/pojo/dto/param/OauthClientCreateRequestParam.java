package com.cdk8s.tkey.management.pojo.dto.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OauthClientCreateRequestParam implements Serializable {

	private static final long serialVersionUID = -4655506034165794097L;

	@NotBlank(message = "clientName 不能为空")
	@Length(max = 35, message = "clientName 最大长度为 35")
	private String clientName;

	@NotBlank(message = "clientId 不能为空")
	@Length(min = 5, max = 20, message = "clientId 长度必须在 5 ~ 20 位")
	@Pattern(regexp = "[A-Za-z0-9_]{1,}", message = "clientId 可以由数字、字母、下划线组合")
	private String clientId;

	@NotBlank(message = "clientSecret 不能为空")
	@Length(min = 32, max = 32, message = "clientSecret 长度必须为 32 位")
	@Pattern(regexp = "[A-Za-z0-9]{1,}", message = "clientSecret 可以由数字、字母组合")
	private String clientSecret;

	@NotBlank(message = "链接地址不能为空")
	@Length(max = 200, message = "链接地址最大长度为 200")
	private String clientUrl;

	@NotBlank(message = "描述不能为空")
	@Length(max = 50, message = "描述最大长度为 50")
	private String clientDesc;

	@NotBlank(message = "logo 链接地址不能为空")
	@Length(max = 200, message = "链接地址最大长度为 200")
	@Pattern(regexp = "[a-zA-z]+://[^\\s]*", message = "logo 链接地址必须包含：http:// 或 https:// 等前缀")
	private String logoUrl;

	@NotNull(message = "排序不能为空")
	@Range(min = 1, max = 100, message = "排序范围只能在 1 ~ 100 的正整数之间，值越小，排序越靠前")
	private Integer ranking;

	private String remark;


}
