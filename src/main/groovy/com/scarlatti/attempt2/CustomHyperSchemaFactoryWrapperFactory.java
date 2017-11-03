package com.scarlatti.attempt2;

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
public class CustomHyperSchemaFactoryWrapperFactory extends WrapperFactory {
    @Override
    public SchemaFactoryWrapper getWrapper(SerializerProvider p) {
        return new CustomHyperSchemaFactoryWrapper(p);
    }

    @Override
    public SchemaFactoryWrapper getWrapper(SerializerProvider p, VisitorContext rvc)
    {
        return new CustomHyperSchemaFactoryWrapper(p).setVisitorContext(rvc);
    }
}
