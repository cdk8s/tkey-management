package com.cdk8s.tkey.management.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomLongConverter extends StdSerializer<Long> {
	public CustomLongConverter() {
		super(Long.class);
	}

	@SneakyThrows
	@Override
	public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers) {
		if (value.toString().length() > 15) {
			gen.writeString(value.toString());
		} else {
			gen.writeNumber(value);
		}
	}
}
