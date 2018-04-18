package com.scarlatti;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import com.fasterxml.jackson.databind.ser.*;
import com.fasterxml.jackson.module.jsonSchema.customProperties.HyperSchemaFactoryWrapper;
import com.fasterxml.jackson.module.jsonSchema.factories.*;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;
import com.scarlatti.model.Penguin;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by pc on 10/25/2017.
 */
public class SchemaDemoOld {
    public static void main(String[] args) throws Exception {
        new SchemaDemoOld().run();
    }

    public void run() throws Exception {
        System.out.println("hello Java!");

        ObjectMapper mapper = new ObjectMapper();
        HyperSchemaFactoryWrapper rootFactory = new TestHyperSchemaFactoryWrapper();
        mapper.setSerializerProvider(new TestDefaultSerializerProvider());
        mapper.acceptJsonFormatVisitor(Penguin.class, rootFactory);
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootFactory.finalSchema()));
    }

    private static class TestDefaultSerializerProvider extends DefaultSerializerProvider {

        public TestDefaultSerializerProvider() { super(); }
        public TestDefaultSerializerProvider(TestDefaultSerializerProvider src) { super(src); }

        protected TestDefaultSerializerProvider(SerializerProvider src, SerializationConfig config,
                       SerializerFactory f) {
            super(src, config, f);
        }

        @Override
        public JsonSerializer<Object> findValueSerializer(JavaType valueType, BeanProperty property) throws JsonMappingException {
            if (Enum.class.isAssignableFrom(valueType.getRawClass())) {
                System.out.println("found an enum...");
                // now return my own enum serializer
                @SuppressWarnings("unchecked")
                JsonSerializer<Object> ser = new JsonSerializer<Object>() {

                    // not called...
                    @Override
                    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
                        System.out.println("serializing object " + value);
                    }

                    @Override
                    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType type) throws JsonMappingException {
                        System.out.println("accepting json format visitor...");

                        StringVisitor stringVisitor = (StringVisitor)visitor.expectStringFormat(type);  // hopefully, tell the visitor to build a string (custom enum string) schema object
//                        objectVisitor.setVisitorContext(null);  // I only needed to do this when it was an ObjectVisitor

//                        ((ObjectVisitor)objectVisitor).getSchema().putProperty("duckduck", new JsonSchema() {
//                            @Override
//                            public JsonFormatTypes getType() {
//                                return JsonFormatTypes.STRING;
//                            }
//                        });

                        // TODO try to add a value to my special field...
                        JsonEnumStringSchema schema = (JsonEnumStringSchema)stringVisitor.getSchema();

//                        schema.specialFieldThatIHopeWillBeSerializedToTheSchema = "what do you know!";
                        schema.enumValues = new ArrayList<>();



                        for (Object uncastEnumConst : type.getRawClass().getEnumConstants()) {
                            Enum<?> castEnumConst = (Enum)uncastEnumConst;

                            Map<String, Object> fields = new HashMap<>();

                            for (Field field : castEnumConst.getClass().getDeclaredFields()) {
                                try {
                                    if (Modifier.isPrivate(field.getModifiers()) && !field.isSynthetic()) {
                                        field.setAccessible(true);
                                        Object val = field.get(castEnumConst);
                                        fields.put(field.getName(), val);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            if (fields.size() > 0) schema.enumValues.add(fields);

                        }

//                        super.acceptJsonFormatVisitor(visitor, type);
                    }
                };

                return ser;
            }

            System.out.println("find value serializer for type " + valueType + " and property " + property);
            return super.findValueSerializer(valueType, property);
        }



        @Override
        public DefaultSerializerProvider copy()
        {
            if (getClass() != TestDefaultSerializerProvider.class) {
                return super.copy();
            }
            return new TestDefaultSerializerProvider(this);
        }

        @Override
        public TestDefaultSerializerProvider createInstance(SerializationConfig config, SerializerFactory jsf) {
            System.out.println("create instance...");
            return new TestDefaultSerializerProvider(this, config, jsf);
        }
    }

    private static class TestFormatVisitorFactory extends FormatVisitorFactory {

        public TestFormatVisitorFactory() {
            super();
        }

        public TestFormatVisitorFactory(WrapperFactory wrapperFactory) {
            super(wrapperFactory);
        }

        // not yet being called for stringformat visitor...
        @Override
        public JsonStringFormatVisitor stringFormatVisitor(StringSchema stringSchema) {
            // now this is being called!
            return new StringVisitor(stringSchema) {

                @Override
                public void enumTypes(Set<String> enums) {
                    System.out.println("enum types...");
                    super.enumTypes(enums);
                }

                @Override
                public void format(JsonValueFormat format) {
                    System.out.println("format...");
                    super.format(format);
                }

            };
        }

        // TODO I can make a factory method for my own custom ENUM JsonSchema
        // TODO this method should only be called when we have an enum...
        JsonStringFormatVisitor jsonEnumStringFormatVisitor() {
            return super.stringFormatVisitor(new JsonEnumStringSchema());
        }

        @Override
        protected JsonObjectFormatVisitor objectFormatVisitor(SerializerProvider provider, ObjectSchema objectSchema, VisitorContext rvc) {
            System.out.println("getting object format visitor");
            JsonObjectFormatVisitor v = super.objectFormatVisitor(provider, objectSchema, rvc);
            return v;
        }
    };

    private static class TestHyperSchemaFactoryWrapper extends HyperSchemaFactoryWrapper {

        TestHyperSchemaFactoryWrapper() {
            visitorFactory = new TestFormatVisitorFactory(new TestHyperSchemaFactoryWrapperFactory());
        }

        public TestHyperSchemaFactoryWrapper(SerializerProvider p) {
            super(p);
            visitorFactory = new TestFormatVisitorFactory(new TestHyperSchemaFactoryWrapperFactory());
        }

        @Override
        public JsonStringFormatVisitor expectStringFormat(JavaType convertedType) {
            System.out.println("expect string format: " + convertedType);
            // TODO I can perform logic to determine whether to call super()
            // TODO or whether to call my own factory method.
            if (Enum.class.isAssignableFrom(convertedType.getRawClass())) {

                JsonStringFormatVisitor visitor = ((TestFormatVisitorFactory)visitorFactory).jsonEnumStringFormatVisitor();
                schema = ((StringVisitor)visitor).getSchema();
                return visitor;
            }
            return super.expectStringFormat(convertedType);
        }

        @Override
        public JsonObjectFormatVisitor expectObjectFormat(JavaType convertedType) {
            System.out.println("expect object format: " + convertedType);

            JsonObjectFormatVisitor v = super.expectObjectFormat(convertedType);
            return v;
        }

        @Override
        public JsonArrayFormatVisitor expectArrayFormat(JavaType convertedType) {
            System.out.println("expect array format: " + convertedType);

            return super.expectArrayFormat(convertedType);
        }
    };

    private static class TestHyperSchemaFactoryWrapperFactory extends WrapperFactory {



        @Override
        public SchemaFactoryWrapper getWrapper(SerializerProvider p) {
            return new TestHyperSchemaFactoryWrapper(p);
        };

        @Override
        public SchemaFactoryWrapper getWrapper(SerializerProvider p, VisitorContext rvc)
        {
            return new TestHyperSchemaFactoryWrapper(p).setVisitorContext(rvc);
        }
    };

    private static class JsonEnumStringSchema extends StringSchema {
        @JsonProperty
        String specialFieldThatIHopeWillBeSerializedToTheSchema;

        // TODO I could add another property on here to get the enum values:
        @JsonProperty("enum")
        List<Object> enumValues;

        public String getSpecialFieldThatIHopeWillBeSerializedToTheSchema() {
            return specialFieldThatIHopeWillBeSerializedToTheSchema;
        }

        public void setSpecialFieldThatIHopeWillBeSerializedToTheSchema(String specialFieldThatIHopeWillBeSerializedToTheSchema) {
            this.specialFieldThatIHopeWillBeSerializedToTheSchema = specialFieldThatIHopeWillBeSerializedToTheSchema;
        }

        public List<Object> getEnumValues() {
            return enumValues;
        }

        public void setEnumValues(List<Object> enumValues) {
            this.enumValues = enumValues;
        }
    }

}
