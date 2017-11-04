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
public class ParentPenguin extends Penguin {
    private Penguin spouse;

    public Penguin getSpouse() {
        return spouse;
    }

    public void setSpouse(Penguin spouse) {
        this.spouse = spouse;
    }
}
