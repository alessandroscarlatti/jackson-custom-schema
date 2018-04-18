package com.scarlatti.lib.schemas;

import java.util.HashMap;
import java.util.Map;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Saturday, 11/4/2017
 *
 * TODO for later use...
 */
public class XProps {
    private Map<String, Object> props;

    public XProps() {
        props = new HashMap<>();
    }

    public Object get(String propName) {
        return props.get(propName);
    }

    public void set(String propName, Object propValue) {
        props.put(propName, propValue);
    }
}
