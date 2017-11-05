package com.scarlatti.attempt2.visitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.VisitorContext;
import com.fasterxml.jackson.module.jsonSchema.factories.WrapperFactory;
import com.scarlatti.attempt2.visitors.CustomJsonFormatVisitor;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 11/2/2017
 */
public class CustomVisitorFactory extends WrapperFactory {
//    // TODO do I really need this anymore, if ever!?
//    @Override
//    public SchemaFactoryWrapper getWrapper(SerializerProvider p) {
//        return new SchemaFactoryWrapper(p);
//    }

    /**
     * This method is important.  It makes sure that the wrapper
     * every visitor uses is our custom wrapper!
     *
     * @param p   serializer provider
     * @param rvc visitor context
     * @return our custom schema factory wrapper!
     */
    @Override
    public SchemaFactoryWrapper getWrapper(SerializerProvider p, VisitorContext rvc) {
        return new CustomJsonFormatVisitor(p).setVisitorContext(new NoRefVisitorContext());
    }

    /**
     * This visitor context will always tell the visitor that this javaType has never been seen.
     * That means that the entire schema for that javaType will be built all over again into the resulting schema.
     */
    private static class NoRefVisitorContext extends VisitorContext {
        @Override
        public String getSeenSchemaUri(JavaType aSeenSchema) {
            return null;
        }
    }
}