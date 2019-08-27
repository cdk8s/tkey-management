package com.cdk8s.tkey.management.mapper;

import com.cdk8s.tkey.management.config.CustomBaseMapper;
import com.cdk8s.tkey.management.pojo.dto.param.OauthClientPageQueryParam;
import com.cdk8s.tkey.management.pojo.entity.OauthClient;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OauthClientMapper extends CustomBaseMapper<OauthClient> {

	List<OauthClient> selectByPageQueryParam(OauthClientPageQueryParam queryParam);

	List<OauthClient> selectByIdIn(@Param("idList") List<Long> idList);

	int updateDeleteEnumByIdList(@Param("updatedDeleteEnum") Integer updatedDeleteEnum, @Param("idList") List<Long> idList);

	int updateStateEnumByIdList(@Param("updatedStateEnum") Integer updatedStateEnum, @Param("idList") List<Long> idList);

}
