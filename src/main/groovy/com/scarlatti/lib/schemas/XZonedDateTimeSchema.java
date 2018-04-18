package com.scarlatti.lib.schemas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.module.jsonSchema.types.ObjectSchema;
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
public class XZonedDateTimeSchema extends ObjectSchema {

    /**
     * This behavior is such that xProps will not even
     * appear in the schema if it has not been instantiated.
     */
    @JsonProperty
    private String specialFormat;

    /**
     * Override the "enum" property to accept my custom enum schemas.
     */
    @JsonProperty("enum")
    private List<Object> enumValues;

    public String getSpecialFormat() {
        return specialFormat;
    }

    public void setSpecialFormat(String specialFormat) {
        this.specialFormat = specialFormat;
    }

    public List<Object> getEnumValues() {
        return enumValues;
    }

    public void setEnumValues(List<Object> enumValues) {
        this.enumValues = enumValues;
    }
}
