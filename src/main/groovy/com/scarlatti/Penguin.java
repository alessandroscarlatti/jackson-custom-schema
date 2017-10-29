package com.scarlatti;

import java.util.List;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 10/26/2017
 */
public class Penguin {
    private List<Feather> feathers;
    private FurColor furColor;

    public List<Feather> getFeathers() {
        return feathers;
    }

    public void setFeathers(List<Feather> feathers) {
        this.feathers = feathers;
    }

    public FurColor getFurColor() {
        return furColor;
    }

    public void setFurColor(FurColor furColor) {
        this.furColor = furColor;
    }
}
