package com.scarlatti.lib.serializers;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.module.jsonSchema.factories.StringVisitor;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;
import com.scarlatti.lib.schemas.XStringSchema;
import com.scarlatti.lib.schemas.XZonedDateTimeSchema;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *       _____                                    __
 *      (, /  |  /)                /)         (__/  )      /)        ,
 *        /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 *     ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 *    (_/                                   (_/
 *    Thursday, 11/2/2017
 * </pre>
 * <p>
 * A serializer happens to be the place that Jackson ALSO places the
 * visitable service.  This is probably so every object that can be
 * written must also have the responsibility to determine how it can
 * be visited.
 * <p>
 * TODO could I extend a base implementation so that I wouldn't
 * have to implement the serialize() method?
 * Doesn't look like it, unless I extend a base class of my own.
 */
public class XZonedDateTimeSerializer extends XSerializer {

    /**
     * N.B. This serializer only intended to be called to accept a visitor if the value is an enum.
     * This is an assumption made. This method only supports being called
     * with a CustomJsonFormatVisitor and an Enum.
     *
     * @param visitor the visitor, which is actually my CustomJsonFormatVisitor
     * @param type    the class of the node being visited, in this case, assumed to be an enum.
     * @throws JsonMappingException on mapping failure (not expected in this case).
     * @throws RuntimeException     on being called with anything other than a CustomJsonFormatVisitor and an Enum
     */
    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType type) throws JsonMappingException {
        if (!(ZonedDateTime.class.isAssignableFrom(type.getRawClass())))
            throw new RuntimeException("Custom Enum Serializer should only be called for ZonedDateTime classes, " +
                "but was called for class " + type.getRawClass().getName());

        StringVisitor stringVisitor = (StringVisitor) visitor.expectStringFormat(type);

        // modify the schema that already exists in the visitor!
        // setSchema() is not allowed!
        // N.B. This assumes tha the visitor has already been given the ZonedDateTimeSchema!
        StringSchema schema = stringVisitor.getSchema();
        schema.setFormat(JsonValueFormat.DATE_TIME);
    }
}
