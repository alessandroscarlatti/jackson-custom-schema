package com.scarlatti.lib.schemas;

import com.fasterxml.jackson.module.jsonSchema.factories.JsonSchemaFactory;
import com.fasterxml.jackson.module.jsonSchema.types.*;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Saturday, 11/4/2017
 *
 * In this class, I can return my own custom schemas transparently to the visiting system.
 */
public class CustomSchemaFactory extends JsonSchemaFactory {

    @Override
    public AnySchema anySchema() {
        return super.anySchema();
    }

    @Override
    public ArraySchema arraySchema() {
        return super.arraySchema();
    }

    @Override
    public BooleanSchema booleanSchema() {
        return super.booleanSchema();
    }

    @Override
    public IntegerSchema integerSchema() {
        return super.integerSchema();
    }

    @Override
    public NullSchema nullSchema() {
        return super.nullSchema();
    }

    @Override
    public NumberSchema numberSchema() {
        return super.numberSchema();
    }

    @Override
    public ObjectSchema objectSchema() {
        return super.objectSchema();
    }

    @Override
    public StringSchema stringSchema() {
        return new XStringSchema();
    }
}
