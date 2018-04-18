package com.scarlatti.lib.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;

import java.util.List;
import java.util.Map;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Saturday, 11/4/2017
 *
 * An extensible string schema.
 */
public class XStringSchema extends StringSchema {

    /**
     * This behavior is such that xProps will not even
     * appear in the schema if it has not been instantiated.
     */
    @JsonProperty
    Map<String, Object> xProps;

    /**
     * Override the "enum" property to accept my custom enum schemas.
     */
    @JsonProperty("enum")
    private List<Object> enumValues;

    public Map<String, Object> getxProps() {
        return xProps;
    }

    public void setxProps(Map<String, Object> xProps) {
        this.xProps = xProps;
    }

    public List<Object> getEnumValues() {
        return enumValues;
    }

    public void setEnumValues(List<Object> enumValues) {
        this.enumValues = enumValues;
    }
}
