package com.cdk8s.tkey.management.pojo.dto.param.bases;

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
public class PageParam implements Serializable {

	private static final long serialVersionUID = 2724187867309137470L;

	@NotNull(message = "当前页为空")
	@Range(min = 1, max = 1000, message = "当前页最小值 1，最大值 1000")
	private Integer pageNum;

	@NotNull(message = "每页数量为空")
	@Range(min = 1, max = 100, message = "每页数量最大值 100")
	private Integer pageSize;

	@Range(min = 1, message = "开始时间最小值 1")
	private Long startDate;

	@Range(min = 1, message = "结束时间最小值 1")
	private Long endDate;

}
