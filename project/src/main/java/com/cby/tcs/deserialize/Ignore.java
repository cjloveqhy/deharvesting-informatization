package com.cby.tcs.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

// 一个空的反序列化器，用于告诉Jackson忽略该属性
public class Ignore extends StdDeserializer<Object> {
    public Ignore() {
        this(null);
    }

    public Ignore(Class<?> vc) {
        super(vc);
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return null; // 或者您可以返回一个默认值
    }
}