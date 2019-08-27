package com.cdk8s.tkey.management.pojo.dto.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OauthClientUpdateRequestParam extends OauthClientCreateRequestParam implements Serializable {

	private static final long serialVersionUID = -6344514816087996120L;

	@NotNull(message = "ID 不能为空")
	@Range(min = 1, message = "ID 最小值为 1")
	private Long id;

}
