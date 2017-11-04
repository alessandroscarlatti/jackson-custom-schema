package com.scarlatti.model;

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
    private List<Feather> feathers2;
    private FurColor furColor;
    private FurColor furColor2;

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

    public List<Feather> getFeathers2() {
        return feathers2;
    }

    public void setFeathers2(List<Feather> feathers2) {
        this.feathers2 = feathers2;
    }

    public FurColor getFurColor2() {
        return furColor2;
    }

    public void setFurColor2(FurColor furColor2) {
        this.furColor2 = furColor2;
    }
}
