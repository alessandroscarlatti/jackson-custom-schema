package com.scarlatti;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scarlatti.lib.CustomSerializerProvider;
import com.scarlatti.lib.visitors.CustomJsonFormatVisitor;
import com.scarlatti.model.FurColor;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 11/2/2017
 */
public class SchemaDemo2 {
    public static void main(String[] args) throws Exception {
        new SchemaDemo2().run();
    }

    public void run() throws Exception {
        System.out.println("hello Java!");

        // use an object mapper.  No big deal.
        ObjectMapper mapper = new ObjectMapper();

        // Use a custom serializer provider.
        // This allows us to ?
        // Do we need custom serializing?  Yes.
        mapper.setSerializerProvider(new CustomSerializerProvider(mapper.getSerializerProvider(), mapper.getSerializationConfig(), mapper.getSerializerFactory()));

        // configure a schema factory (get a wrapper)
        // this factory will hold a working state of the
        // schema (read hierarchical structure of POJOs)
        CustomJsonFormatVisitor visitor = new CustomJsonFormatVisitor();

        // Tell the object mapper to accept a schema factory for JSON format visiting!
        // Again, this factory will HOLD the working, and eventually final schema.
        mapper.acceptJsonFormatVisitor(FurColor.class, visitor);

        // now just tell Jackson to serialize the POJO structure.
        // That is our "schema".
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(visitor.finalSchema()));
    }
}