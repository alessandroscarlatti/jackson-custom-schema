package com.scarlatti.attempt2;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import com.fasterxml.jackson.module.jsonSchema.factories.FormatVisitorFactory;
import com.fasterxml.jackson.module.jsonSchema.factories.StringVisitor;
import com.fasterxml.jackson.module.jsonSchema.factories.VisitorContext;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;
import com.scarlatti.App;

import java.util.Set;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 11/2/2017
 */
public class CustomFormatVisitorFactory extends FormatVisitorFactory {
    public CustomFormatVisitorFactory() {
        super();
    }

    public CustomFormatVisitorFactory(WrapperFactory wrapperFactory) {
        super(wrapperFactory);
    }

    // not yet being called for stringformat visitor...
    @Override
    public JsonStringFormatVisitor stringFormatVisitor(StringSchema stringSchema) {
        // now this is being called!
        return new StringVisitor(stringSchema) {

            @Override
            public void enumTypes(Set<String> enums) {
                System.out.println("enum types...");
                super.enumTypes(enums);
            }

            @Override
            public void format(JsonValueFormat format) {
                System.out.println("format...");
                super.format(format);
            }

        };
    }

    // TODO I can make a factory method for my own custom ENUM JsonSchema
    // TODO this method should only be called when we have an enum...
    JsonStringFormatVisitor jsonEnumStringFormatVisitor() {
        return super.stringFormatVisitor(new CustomEnumSchema());
    }

    @Override
    protected JsonObjectFormatVisitor objectFormatVisitor(SerializerProvider provider, ObjectSchema objectSchema, VisitorContext rvc) {
        System.out.println("getting object format visitor");
        JsonObjectFormatVisitor v = super.objectFormatVisitor(provider, objectSchema, rvc);
        return v;
    }
}
