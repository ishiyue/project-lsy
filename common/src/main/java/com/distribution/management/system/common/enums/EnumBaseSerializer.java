package com.distribution.management.system.common.enums;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;


/**
 * @author admin
 */
public class EnumBaseSerializer implements ObjectSerializer {


    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object instanceof ContentEnum) {
            ContentEnum contentEnum = (ContentEnum) object;
            out.writeInt(contentEnum.getValue());
        } else {
            out.writeEnum((Enum<?>) object);
        }
    }
}

