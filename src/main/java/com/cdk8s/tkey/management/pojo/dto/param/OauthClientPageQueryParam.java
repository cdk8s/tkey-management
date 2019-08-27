package com.cdk8s.tkey.management.pojo.dto.param;

import com.cdk8s.tkey.management.pojo.dto.param.bases.PageParam;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;


@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OauthClientPageQueryParam extends PageParam {

	private static final long serialVersionUID = 3185188029842908897L;

	private String clientName;
	private String clientId;

	@Range(min = 1, max = 2, message = "状态值只能是 1：启用 和 2：禁用")
	private Integer stateEnum;


}
