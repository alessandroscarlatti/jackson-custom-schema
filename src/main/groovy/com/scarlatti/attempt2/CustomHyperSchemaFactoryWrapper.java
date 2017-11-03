package com.scarlatti.attempt2;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.customProperties.HyperSchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.StringVisitor;
import com.scarlatti.App;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 11/2/2017
 */
public class CustomHyperSchemaFactoryWrapper extends HyperSchemaFactoryWrapper {
    CustomHyperSchemaFactoryWrapper() {
        visitorFactory = new CustomFormatVisitorFactory(new CustomHyperSchemaFactoryWrapperFactory());
    }

    public CustomHyperSchemaFactoryWrapper(SerializerProvider p) {
        super(p);
        visitorFactory = new CustomFormatVisitorFactory(new CustomHyperSchemaFactoryWrapperFactory());
    }

    @Override
    public JsonStringFormatVisitor expectStringFormat(JavaType convertedType) {
        System.out.println("expect string format: " + convertedType);
        // TODO I can perform logic to determine whether to call super()
        // TODO or whether to call my own factory method.
        if (Enum.class.isAssignableFrom(convertedType.getRawClass())) {

            JsonStringFormatVisitor visitor = ((CustomFormatVisitorFactory)visitorFactory).jsonEnumStringFormatVisitor();
            schema = ((StringVisitor)visitor).getSchema();
            return visitor;
        }
        return super.expectStringFormat(convertedType);
    }

    @Override
    public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {
        System.out.println("expect object format: " + convertedType);

        JsonObjectFormatVisitor v = super.expectObjectFormat(convertedType);
        return v;
    }

    @Override
    public JsonArrayFormatVisitor expectArrayFormat(JavaType convertedType) {
        System.out.println("expect array format: " + convertedType);

        return super.expectArrayFormat(convertedType);
    }
}
