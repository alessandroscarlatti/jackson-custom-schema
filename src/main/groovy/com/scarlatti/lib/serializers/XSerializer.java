package com.scarlatti.lib.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Saturday, 11/4/2017
 */
public abstract class XSerializer extends JsonSerializer<Object> {

    /**
     * N.B. I don't need this method, except that I need it in order to
     * instantiate this abstract class to create a visitable object.
     */
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) {
        System.out.println("serializing object " + value);
    }

//    public abstract void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType type, XProps xProps) throws JsonMappingException;
}
