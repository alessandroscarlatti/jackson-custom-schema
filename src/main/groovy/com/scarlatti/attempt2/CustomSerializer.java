package com.scarlatti.attempt2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.StringVisitor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 11/2/2017
 *
 * A serializer happens to be the place that Jackson ALSO places the
 * visitable service.  This is probably so every object that can be
 * written must also have the responsibility to determine how it can
 * be visited.
 *
 * TODO could I extend a base implementation so that I wouoldn't
 * have to implement the serialize() method?
 *
 */
public class CustomSerializer extends JsonSerializer<Object> {
    /**
     * I don't need this method, except that I need to
     * provide a visitable object.
     */
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        System.out.println("serializing object " + value);
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType type) throws JsonMappingException {
        System.out.println("accepting json format visitor...");

        // hopefully, tell the visitor to build a string (custom enum string) schema object
        StringVisitor stringVisitor = (StringVisitor)visitor.expectStringFormat(type);
//                        objectVisitor.setVisitorContext(null);  // I only needed to do this when it was an ObjectVisitor
        // TODO ^^ would this get rid of the $ref?

        // TODO try to add a value to my special field...
        CustomEnumSchema schema = (CustomEnumSchema)stringVisitor.getSchema();

        schema.setEnumValues(new ArrayList<>());

        for (Object uncastEnumConst : type.getRawClass().getEnumConstants()) {
            Enum<?> castEnumConst = (Enum)uncastEnumConst;

            Map<String, Object> fields = new HashMap<>();

            for (Field field : castEnumConst.getClass().getDeclaredFields()) {
                try {
                    if (Modifier.isPrivate(field.getModifiers()) && !field.isSynthetic()) {
                        field.setAccessible(true);
                        Object val = field.get(castEnumConst);
                        fields.put(field.getName(), val);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (fields.size() > 0) schema.getEnumValues().add(fields);
        }
    }

    private static void buildEnumSchemaFields(CustomEnumSchema schema, Class<Enum<?>> clazz) {
        schema.setEnumValues(new ArrayList<>());

        for (Object uncastEnumConst : clazz.getEnumConstants()) {
            Enum<?> castEnumConst = (Enum)uncastEnumConst;

            // use a map to put in the enum POJO.
            // This will make Jackson serialize each field in a typical way.
            // Any instance enum field will not be serialized with schema
            // unless we implement that.
            Map<String, Object> fields = new HashMap<>();

            for (Field field : castEnumConst.getClass().getDeclaredFields()) {
                try {
                    if (Modifier.isPrivate(field.getModifiers()) && !field.isSynthetic()) {
                        field.setAccessible(true);
                        Object val = field.get(castEnumConst);
                        fields.put(field.getName(), val);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (fields.size() > 0) schema.getEnumValues().add(fields);
        }
    }
}
