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
import java.util.EnumMap;
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
public class EnumSerializer extends JsonSerializer<Object> {
    /**
     * I don't need this method, except that I need it in order to
     * instantiate this abstract class to create a visitable object.
     */
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) {
        System.out.println("serializing object " + value);
    }

    /**
     * This serializer only gets called to accept a visitor if the value is an enum.
     * This is an assumption made.
     *
     * @param visitor the visitor
     * @param type the class of the node being visited, in this case, assumed to be an enum.
     * @throws JsonMappingException on mapping failure (not expected in this case).
     * @throws RuntimeException on failure to map the enum.
     */
    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType type) throws JsonMappingException {
        System.out.println("accepting json format visitor...");

        // hopefully, tell the visitor to build a string (custom enum string) schema object
        // TODO can this be returned as StringVisitor?
        StringVisitor stringVisitor = (StringVisitor)visitor.expectStringFormat(type);
//                        objectVisitor.setVisitorContext(null);  // I only needed to do this when it was an ObjectVisitor
        // TODO ^^ would this get rid of the $ref?  Yes, but in an serializer for objects, not enums, unless this one could do double duty!...
        // TODO what happens with an enum inside an enum?

        CustomEnumSchema schema = (CustomEnumSchema)stringVisitor.getSchema();
        buildEnumSchemaFields(schema, type.getRawClass());
    }

    /**
     * Helper method to build enum schema fields.
     * No failure if class is not an enum class.
     *
     * @param schema the empty schema object.
     * @param clazz the given enum class.
     */
    private static void buildEnumSchemaFields(CustomEnumSchema schema, Class<?> clazz) {
        schema.setEnumValues(new ArrayList<>());                        // start with an empty list

        for (Object uncastEnumConst : clazz.getEnumConstants()) {       // use a map to put in the enum POJO.
            Enum<?> castEnumConst = (Enum)uncastEnumConst;              // This will make Jackson serialize each field in a typical way.
            schema.getEnumValues().add(getEnumFields(castEnumConst));   // Any instance enum field will not be serialized with schema
        }                                                               // unless we implement that.
    }                                                                   // add the fieldMap to the schema POJO, and we're done!


    /**
     * Helper method to get a map of the field values for this enum.
     * @param enumConst the enum to evaluate
     * @return the map of field values
     */
    private static Map<String, Object> getEnumFields(Enum<?> enumConst) {
        Map<String, Object> fieldMap = new HashMap<>();

        // we only want the specific fields for the declared enum class.
        // we don't want the synthetically generated fields, $values, for example.
        // but we don't want public fields either?
        for (Field field : enumConst.getClass().getDeclaredFields()) {
            try {
                // TODO confirm that we definitely want isPrivate()
                if (Modifier.isPrivate(field.getModifiers()) && !field.isSynthetic()) {
                    field.setAccessible(true);

                    // if the field is an enum, use my custom serialization for it!
//                    if (Enum.class.isAssignableFrom(field.getType())) {
//                        CustomEnumSchema schema = new CustomEnumSchema();
//                        buildEnumSchemaFields(schema, field.getType());
//                        fieldMap.put(field.getName(), schema);
//                    } else
                        fieldMap.put(field.getName(), field.get(enumConst));  // add the field to the map
                }
            } catch (Exception e) {
                throw new RuntimeException("Error building schema with class: " +
                    enumConst.getDeclaringClass().getName() + ", enum: " + enumConst.name());
            }
        }

        return fieldMap;
    }
}
