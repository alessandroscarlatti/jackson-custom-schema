package com.scarlatti.attempt2;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.module.jsonSchema.factories.FormatVisitorFactory;
import com.fasterxml.jackson.module.jsonSchema.factories.VisitorContext;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 11/2/2017
 */
public class CustomFormatVisitorFactory extends FormatVisitorFactory {

    public CustomFormatVisitorFactory(WrapperFactory wrapperFactory) {
        super(wrapperFactory);
    }

    // I can make a factory method for my own custom ENUM JsonSchema
    // TODO this method should only be called when we have an enum...

    /**
     * This is a factory method for my own custom Enum Schema POJO visitor.
     * @return the visitor
     */
    JsonStringFormatVisitor jsonEnumStringFormatVisitor() {
        return super.stringFormatVisitor(new CustomEnumSchema());
    }

    @Override
    protected JsonObjectFormatVisitor objectFormatVisitor(SerializerProvider provider, ObjectSchema objectSchema, VisitorContext rvc) {
        System.out.println("getting object format visitor");
        // what if I put null for visitor context here?  Null pointer exception.
        JsonObjectFormatVisitor v = super.objectFormatVisitor(provider, objectSchema, rvc);
        return v;
    }
}
