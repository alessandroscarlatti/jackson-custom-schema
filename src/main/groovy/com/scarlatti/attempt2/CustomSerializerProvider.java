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
 *
 * The serializer provider provides Jackson with serializers
 * (which each have visitable methods) for the object hierarchy.
 *
 * DefaultSerializerProvider is abstract!
 */
public class CustomSerializerProvider extends DefaultSerializerProvider {

    // must have constructor for abstract class
    protected CustomSerializerProvider(SerializerProvider src, SerializationConfig config, SerializerFactory f) {
        super(src, config, f);
    }

    /**
     * @param valueType the visitor is asking for a serializer for this value type
     * @param property we will ignore for which property the visitor is asking
     * @return the serializer to use (for visitable)
     * @throws JsonMappingException if the mapping fails (it shouldn't!)
     */
    @Override
    public JsonSerializer<Object> findValueSerializer(JavaType valueType, BeanProperty property) throws JsonMappingException {
        if (Enum.class.isAssignableFrom(valueType.getRawClass())) {
            System.out.println("found an enum...");
            // now return my own enum serializer
            return new EnumSerializer();
        }

        System.out.println("find value serializer for type " + valueType + " and property " + property);
        return super.findValueSerializer(valueType, property);
    }

    // Do I need this?  Technically, yes since it's an abstract method in the DefaultSerializerProvider
    @Override
    public CustomSerializerProvider createInstance(SerializationConfig config, SerializerFactory jsf) {
        return new CustomSerializerProvider(this, config, jsf);
    }
}
