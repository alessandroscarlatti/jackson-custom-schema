package com.scarlatti.attempt2;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 11/2/2017
 * <p>
 * The serializer provider provides Jackson with serializers
 * (which each have visitable methods) for the object hierarchy.
 * <p>
 * DefaultSerializerProvider is abstract!
 */
public class CustomSerializerProvider extends DefaultSerializerProvider {

    /**
     * using this method to build for injection into ObjectMapper.
     */
    public CustomSerializerProvider() {
        super();
    }

    /**
     * must have this constructor for abstract class
     */
    protected CustomSerializerProvider(SerializerProvider src, SerializationConfig config, SerializerFactory f) {
        super(src, config, f);
    }

    /**
     * N.B. Do I need this?  No, it's never called, but YES, since it's an abstract method in the DefaultSerializerProvider
     */
    @Override
    public CustomSerializerProvider createInstance(SerializationConfig config, SerializerFactory jsf) {
        return new CustomSerializerProvider(this, config, jsf);
    }

    /**
     * @param valueType the visitor is asking for a serializer for this value type
     * @param property  we will ignore for which property the visitor is asking
     * @return the serializer to use (for visitable)
     * @throws JsonMappingException if the mapping fails (it shouldn't!)
     */
    @Override
    public JsonSerializer<Object> findValueSerializer(JavaType valueType, BeanProperty property) throws JsonMappingException {
        if (Enum.class.isAssignableFrom(valueType.getRawClass())) {
            System.out.println("found an enum...");
            // now return my own enum serializer
            return new CustomEnumSerializer();
        }

        System.out.println("find value serializer for type " + valueType + " and property " + property);
        return super.findValueSerializer(valueType, property);
    }

    /**
     * TODO Try to get default values from the bean property here...
     *
     * To make that happen, I could subclass this and related classes
     * to include an optional POJO as an object for Default Values.
     *
     * As the visitor gets the value serializer, it adds those values to the schema.
     * This is easy enough to add that field to the custom enum serializer methods and
     * custom enum schema class.  However, I will need to add that value to the schema
     * objects being used by the default schema objects!
     *
     * But how many expect<type>Visitor methods are there anyway?  I can override them
     * without too much trouble.
     *
     * One issue would be the situation where item1 propertyA contains item2 with nested
     * properties A, B, C, and items 2, 3, and 4.
     * The schema would now include that entire structure.  What should it include?
     * Atomic property values (enums are the same as other classes in this case) where present.
     * For all other complex types:
     * - List: include the full object structure?  This way the source of truth for default values
     *         is in one location.  When the visitor drills down into the nested properties of the
     *         list items it will not provide default values.  The user of this sort of schema will
     *         be expected to interpret this behavior (perform logic on the provided default values
     *         for the list).  The visitor can handle this situation by passing null down the chain
     *         as the default value, and other serializers will handle this null value by providing
     *         no default value.
     * - Object: include the full object structure.
     * - Map: include the full map.
     *
     */
}
