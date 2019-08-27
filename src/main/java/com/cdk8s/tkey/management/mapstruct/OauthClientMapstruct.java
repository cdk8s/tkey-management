package com.cdk8s.tkey.management.mapstruct;


import com.cdk8s.tkey.management.pojo.bo.cache.OauthClientToRedisBO;
import com.cdk8s.tkey.management.pojo.dto.param.OauthClientCreateRequestParam;
import com.cdk8s.tkey.management.pojo.dto.param.OauthClientUpdateRequestParam;
import com.cdk8s.tkey.management.pojo.dto.response.OauthClientDTO;
import com.cdk8s.tkey.management.pojo.entity.OauthClient;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper(componentModel = "spring")
public interface OauthClientMapstruct {

	OauthClientDTO toDTO(OauthClient source);

	List<OauthClientDTO> toDTOList(List<OauthClient> source);

	OauthClientToRedisBO toRedisBO(OauthClient source);

	List<OauthClientToRedisBO> toRedisBOList(List<OauthClient> source);

	OauthClient toEntity(OauthClientCreateRequestParam source);

	OauthClient toEntity(OauthClientUpdateRequestParam source);


}
