package com.scarlatti.attempt2;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.VisitorContext;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 11/2/2017
 */
public class CustomSchemaFactoryWrapperFactory extends WrapperFactory {
    @Override
    public SchemaFactoryWrapper getWrapper(SerializerProvider p) {
        return new SchemaFactoryWrapper(p);
    }

    @Override
    public SchemaFactoryWrapper getWrapper(SerializerProvider p, VisitorContext rvc)
    {
        return new CustomSchemaFactoryWrapper(p).setVisitorContext(new NoRefVisitorContext());
    }

    private static class NoRefVisitorContext extends VisitorContext {
        @Override
        public String getSeenSchemaUri(JavaType aSeenSchema) {
            return null;
        }
    }
}