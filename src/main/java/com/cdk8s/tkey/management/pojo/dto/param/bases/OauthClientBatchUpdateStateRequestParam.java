package com.cdk8s.tkey.management.pojo.dto.param.bases;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OauthClientBatchUpdateStateRequestParam implements Serializable {

	private static final long serialVersionUID = 7650165913458884946L;

	@NotEmpty(message = "idList 不能为空")
	@Size(min = 1, message = "idList 至少需要一个元素")
	private List<Long> idList;

	@NotNull(message = "状态不能为空")
	@Range(min = 1, max = 2, message = "状态值只能是 1：启用 和 2：禁用")
	private Integer stateEnum;

}
