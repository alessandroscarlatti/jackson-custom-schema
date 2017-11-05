package com.scarlatti

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper
import com.scarlatti.attempt2.visitors.CustomJsonFormatVisitor
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
            SchemaFactoryWrapper schemaFactory = new CustomJsonFormatVisitor()
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
            SchemaFactoryWrapper schemaFactory = new CustomJsonFormatVisitor()
            mapper.acceptJsonFormatVisitor(FurColor.class, schemaFactory)
            println mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schemaFactory.finalSchema())
        then:
            noExceptionThrown()
    }

    @Test
    void "can get a nested bean property from a separate POJO"() {
        when:
            String expectJson = '''{"type":"object","id":"urn:jsonschema:com:scarlatti:model:Penguin","properties":{"feathers":{"type":"array","items":{"type":"object","id":"urn:jsonschema:com:scarlatti:model:Feather","properties":{"furColor3":{"type":"string","enum":[{"name":"RED","count":0,"description":"redder than red","pigment":{"name":"red"},"choice":"YES"},{"name":"BLUE","count":1,"description":"bluer than blue","pigment":{"name":"blue"},"choice":"NO"},{"name":"BLACK","count":2,"description":"blacker than black","pigment":{"name":"black"},"choice":"NO"}]}}}},"feathers2":{"type":"array","items":{"type":"object","id":"urn:jsonschema:com:scarlatti:model:Feather","properties":{"furColor3":{"type":"string","enum":[{"name":"RED","count":0,"description":"redder than red","pigment":{"name":"red"},"choice":"YES"},{"name":"BLUE","count":1,"description":"bluer than blue","pigment":{"name":"blue"},"choice":"NO"},{"name":"BLACK","count":2,"description":"blacker than black","pigment":{"name":"black"},"choice":"NO"}]}}}},"furColor":{"type":"string","enum":[{"name":"RED","count":0,"description":"redder than red","pigment":{"name":"red"},"choice":"YES"},{"name":"BLUE","count":1,"description":"bluer than blue","pigment":{"name":"blue"},"choice":"NO"},{"name":"BLACK","count":2,"description":"blacker than black","pigment":{"name":"black"},"choice":"NO"}]},"furColor2":{"type":"string","enum":[{"name":"RED","count":0,"description":"redder than red","pigment":{"name":"red"},"choice":"YES"},{"name":"BLUE","count":1,"description":"bluer than blue","pigment":{"name":"blue"},"choice":"NO"},{"name":"BLACK","count":2,"description":"blacker than black","pigment":{"name":"black"},"choice":"NO"}]},"penguinName":{"type":"string"}}}'''
            ObjectMapper mapper = new ObjectMapper()
            mapper.setSerializerProvider(new CustomSerializerProvider())
            SchemaFactoryWrapper schemaFactory = new CustomJsonFormatVisitor()
            mapper.acceptJsonFormatVisitor(Penguin.class, schemaFactory)

            String actualJson = mapper.writeValueAsString(schemaFactory.finalSchema())
            println actualJson

        then:
            noExceptionThrown()
            actualJson == expectJson
    }

}
