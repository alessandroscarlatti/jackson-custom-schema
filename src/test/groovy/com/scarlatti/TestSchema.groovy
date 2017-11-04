package com.scarlatti

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper
import com.scarlatti.attempt2.CustomSchemaFactoryWrapper
import com.scarlatti.attempt2.CustomSerializerProvider
import com.scarlatti.model.FurColor
import com.scarlatti.model.ParentPenguin
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
            println mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schemaFactory.finalSchema())
        then:
            noExceptionThrown()
    }

    @Test
    void "can generate schema for Enum class"() {
        when:
            ObjectMapper mapper = new ObjectMapper()
            mapper.setSerializerProvider(new CustomSerializerProvider())
            SchemaFactoryWrapper schemaFactory = new CustomSchemaFactoryWrapper()
            mapper.acceptJsonFormatVisitor(FurColor.class, schemaFactory)
            println mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schemaFactory.finalSchema())
        then:
            noExceptionThrown()
    }

    @Test
    void "can get a nested bean property from a separate POJO"() {
        when:
            ObjectMapper mapper = new ObjectMapper()
            mapper.setSerializerProvider(new CustomSerializerProvider())
            SchemaFactoryWrapper schemaFactory = new CustomSchemaFactoryWrapper()
            mapper.acceptJsonFormatVisitor(ParentPenguin.class, schemaFactory)
            println mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schemaFactory.finalSchema())
        then:
            noExceptionThrown()
    }

}
