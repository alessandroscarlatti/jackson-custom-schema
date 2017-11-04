package com.scarlatti.attempt2;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.StringVisitor;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 11/2/2017
 */
public class CustomSchemaFactoryWrapper extends SchemaFactoryWrapper {
    public CustomSchemaFactoryWrapper() {
        visitorFactory = new CustomFormatVisitorFactory(new CustomSchemaFactoryWrapperFactory());
    }

    public CustomSchemaFactoryWrapper(SerializerProvider p) {
        super(p);
        visitorFactory = new CustomFormatVisitorFactory(new CustomSchemaFactoryWrapperFactory());
    }

    /**
     * This seems to be the principle hack point so far!
     *
     * This method is called by default when the default visitor encounters an enum.
     * Instead of returning a default StringVisitor, we will return a custom visitor!
     *
     * We will either call super or call my own factory method.
     *
     * @param convertedType the class that was found that should be handled with "string format"
     * @return the visitor that this factory would like to be used by the
     *         serializer already accepting another visitor.
     */
    @Override
    public JsonStringFormatVisitor expectStringFormat(JavaType convertedType) {
        if (Enum.class.isAssignableFrom(convertedType.getRawClass())) {

            // TODO can I build this visitor without the factory?
            JsonStringFormatVisitor visitor = ((CustomFormatVisitorFactory)visitorFactory).jsonEnumStringFormatVisitor();
            schema = ((StringVisitor)visitor).getSchema();
            return visitor;
        } else {
            return super.expectStringFormat(convertedType);
        }
    }
}
