package com.scarlatti;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 10/26/2017
 */
public enum FurColor {
    RED("redder than red", 0),
    BLUE("bluer than blue", 1),
    BLACK("blacker than black", 2), ;

    private String description;
    private Integer count;

    FurColor(String description) {
        this.description = description;
    }

    FurColor(String description, Integer count) {
        this.description = description;
        this.count = count;
    }

    @Override
    public String toString() {
        return "asdfh";
    }
}
