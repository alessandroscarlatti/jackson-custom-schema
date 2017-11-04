package com.scarlatti

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper
import com.scarlatti.attempt2.CustomSchemaFactoryWrapper
import com.scarlatti.attempt2.CustomSerializerProvider
import com.scarlatti.model.Penguin
import org.junit.Test
import spock.lang.Specification

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Saturday, 11/4/2017
 */
class TestSchema extends Specification {

    @Test
    void "can generate schema for POJO"() {
        when:
            ObjectMapper mapper = new ObjectMapper()
            mapper.setSerializerProvider(new CustomSerializerProvider())
            SchemaFactoryWrapper schemaFactory = new CustomSchemaFactoryWrapper()
            mapper.acceptJsonFormatVisitor(Penguin.class, schemaFactory)
            println schemaFactory.finalSchema()
        then:
            noExceptionThrown()
    }


}
