package com.scarlatti.lib.visitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.factories.FormatVisitorFactory;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.scarlatti.lib.CustomSerializerProvider;
import com.scarlatti.lib.schemas.CustomSchemaFactory;
import com.scarlatti.lib.schemas.XZonedDateTimeSchema;

import java.time.ZonedDateTime;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 11/2/2017
 */
public class CustomJsonFormatVisitor extends SchemaFactoryWrapper {
    // this constructor is called initially
    public CustomJsonFormatVisitor() {
        super();
        visitorFactory = new FormatVisitorFactory(new CustomVisitorFactory());  // TODO using FormatVisitorFactory instead
        schemaProvider = new CustomSchemaFactory();  // inject my custom schemas
    }

    /**
     * This constructor is called for the recursively created visitor.
     *
     * @param p the provider to use (presumably the one already in use)
     */
    public CustomJsonFormatVisitor(SerializerProvider p) {
        super(p);
        visitorFactory = new FormatVisitorFactory(new CustomVisitorFactory());  // TODO try leaving this out...
        schemaProvider = new CustomSchemaFactory();  // inject my custom schemas
    }

    @Override
    public JsonAnyFormatVisitor expectAnyFormat(JavaType convertedType) {
        return super.expectAnyFormat(convertedType);
    }

    /**
     * ZonedDateTime winds up here!!
     * @param convertedType the type of the item (field) being considered
     * @return the visitor which will contain the correct schema object
     */
    @Override
    public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {

//        if (convertedType.getRawClass().isAssignableFrom(ZonedDateTime.class)) {
//            return new XZonedDateTimeSchema();
//        }


        return super.expectObjectFormat(convertedType);
    }

//    public JsonStringFormatVisitor expectZonedDateTimeFormat(JavaType convertedType) {
//        return new XZonedDateTimeSchema();
//    }

    /**
     * TODO review this documentation...  I'm hacking it differently now...
     * This seems to be the principle hack point so far!
     * <p>
     * Calling this method WILL set the schema for this visitor.  This is the schema that
     * will be used as the schema for this object!
     * <p>
     * This method is called by default when the default visitor encounters an enum.
     * Instead of returning a default StringVisitor, we will return a custom visitor!
     * <p>
     * <p>
     * We will either call super or call my own factory method.
     *
     * @param convertedType the class that was found that should be handled with "string format"
     * @return the visitor that this factory would like to be used by the
     * serializer already accepting another visitor.
     */
//    public JsonStringFormatVisitor expectEnumFormat(JavaType convertedType) {
//        if (Enum.class.isAssignableFrom(convertedType.getRawClass())) {
//
//            // TODO do I need this anymore, since I am already injecting my custom schema?
//            JsonStringFormatVisitor visitor = ((CustomFormatVisitorFactory) visitorFactory).jsonEnumStringFormatVisitor();
//            schema = ((StringVisitor) visitor).getSchema();  // this is the line that does the dirty work!
//            return visitor;
//        } else {
//            return super.expectStringFormat(convertedType);
//        }
//    }
}
