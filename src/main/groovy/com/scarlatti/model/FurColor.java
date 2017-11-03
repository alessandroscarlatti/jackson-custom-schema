package com.scarlatti.model;

/**
 * ~     _____                                    __
 * ~    (, /  |  /)                /)         (__/  )      /)        ,
 * ~      /---| // _ _  _  _ __  _(/ __ ___     / _ _  __ // _ _/_/_
 * ~   ) /    |(/_(//_)/_)(_(/ ((_(_/ ((_)   ) / (_(_(/ ((/_(_((_(__(_
 * ~  (_/                                   (_/
 * ~  Thursday, 10/26/2017
 */
public enum FurColor {
    RED("redder than red", 0, Choice.YES, new Pigment("red")),
    BLUE("bluer than blue", 1, Choice.NO, new Pigment("blue")),
    BLACK("blacker than black", 2, Choice.NO, new Pigment("black"));

    private String description;
    private Integer count;
    private Choice choice;
    private Pigment pigment;

    FurColor(String description) {
        this.description = description;
    }

    FurColor(String description, Integer count, Choice choice, Pigment pigment) {
        this.description = description;
        this.count = count;
        this.choice = choice;
        this.pigment = pigment;
    }

    @Override
    public String toString() {
        return "asdfh";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Choice getChoice() {
        return choice;
    }

    public void setChoice(Choice choice) {
        this.choice = choice;
    }

    public Pigment getPigment() {
        return pigment;
    }

    public void setPigment(Pigment pigment) {
        this.pigment = pigment;
    }
}
