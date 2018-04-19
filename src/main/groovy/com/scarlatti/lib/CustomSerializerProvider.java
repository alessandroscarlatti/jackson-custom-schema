package com.scarlatti.lib;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.scarlatti.lib.serializers.XEnumSerializer;
import com.scarlatti.lib.serializers.XZonedDateTimeSerializer;

import java.time.ZonedDateTime;

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
    public CustomSerializerProvider(SerializerProvider src, SerializationConfig config, SerializerFactory f) {
        super(src, config, f);
    }

    /**
     * must have this method for abstract class
     */
    @Override
    public CustomSerializerProvider createInstance(SerializationConfig config, SerializerFactory jsf) {
        return new CustomSerializerProvider(this, config, jsf);
    }

    /**
     * The only time that I want to use a custom serializer right now is for enums.
     *
     * @param valueType the visitor is asking for a serializer for this value type
     * @param property  we will ignore for which property the visitor is asking
     * @return the serializer to use (for visitable)
     * @throws JsonMappingException if the mapping fails (it shouldn't!)
     */
    @Override
    public JsonSerializer<Object> findValueSerializer(JavaType valueType, BeanProperty property) throws JsonMappingException {
        if (Enum.class.isAssignableFrom(valueType.getRawClass())) {
            return new XEnumSerializer();  // now return my own enum serializer
        }

        if (ZonedDateTime.class.isAssignableFrom(valueType.getRawClass())) {
            return new XZonedDateTimeSerializer();
        }

        // TODO add support for ZonedDateTime

        return super.findValueSerializer(valueType, property);  // ultimately calls the visitor factory?
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
     * Methods I will need to subclass...and schemas I will need to extend.
     *
     * AnyFormat        when is this used??  How is it distinct from ObjectFormat?  (maybe for Object.class)
     * ArrayFormat
     * BooleanFormat
     * IntegerFormat
     * NullFormat
     * NumberFormat     is this only used when the number is determined not to be IntegerFormat?
     * ObjectFormat     (maybe for any class not any specific format, but NOT Object.class)
     * StringFormat
     * MapFormat
     *
     * Perhaps all my custom schemas should just have a map of custom values?  It could be more extensible...
     * And if the map is empty it won't appear on the schema anyway.  So it doesn't hurt to have it there,
     * and it would probably make further development that much quicker and easier.  Less subclassing!
     */
}
