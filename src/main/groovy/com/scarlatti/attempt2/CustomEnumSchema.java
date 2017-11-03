package com.scarlatti.attempt2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.module.jsonSchema.types.StringSchema;

import java.util.List;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 11/2/2017
 *
 * A schema is built as a hierarchical structure of POJOs.
 * Jackson then serializes the POJOs.
 * This is the POJO I would like Jackson to use to describe an enum.
 */
public class CustomEnumSchema extends StringSchema {

    /**
     * For schema objects, Jackson only serializes @JsonProperty values.
     */
    @JsonProperty("enum")
    private List<Object> enumValues;

    public List<Object> getEnumValues() {
        return enumValues;
    }

    public void setEnumValues(List<Object> enumValues) {
        this.enumValues = enumValues;
    }
}
