package com.scarlatti.attempt2;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 11/2/2017
 */
public class CustomSerializerProvider extends DefaultSerializerProvider {
    public CustomSerializerProvider() { super(); }
    public CustomSerializerProvider(CustomSerializerProvider src) { super(src); }

    protected CustomSerializerProvider(SerializerProvider src, SerializationConfig config,
                                            SerializerFactory f) {
        super(src, config, f);
    }

    @Override
    public JsonSerializer<Object> findValueSerializer(JavaType valueType, BeanProperty property) throws JsonMappingException {
        if (Enum.class.isAssignableFrom(valueType.getRawClass())) {
            System.out.println("found an enum...");
            // now return my own enum serializer
            return new CustomSerializer();
        }

        System.out.println("find value serializer for type " + valueType + " and property " + property);
        return super.findValueSerializer(valueType, property);
    }


    // TODO do I need this?
    @Override
    public DefaultSerializerProvider copy()
    {
        if (getClass() != CustomSerializerProvider.class) {
            return super.copy();
        }
        return new CustomSerializerProvider(this);
    }

    // TODO do I need this?
    @Override
    public CustomSerializerProvider createInstance(SerializationConfig config, SerializerFactory jsf) {
        System.out.println("create instance...");
        return new CustomSerializerProvider(this, config, jsf);
    }
}
