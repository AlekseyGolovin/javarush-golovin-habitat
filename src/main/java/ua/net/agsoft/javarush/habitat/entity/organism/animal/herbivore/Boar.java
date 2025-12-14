package ua.net.agsoft.javarush.habitat.entity.organism.animal.herbivore;

import ua.net.agsoft.javarush.habitat.entity.organism.OrganismParameter;

public class Boar extends Herbivore {
    private static int num = 0;

    private String name;

    public Boar() {
        this.name = this.getClass().getSimpleName() + "_" + getNum();
    }

    @Override
    protected int getNum() {
        return ++num;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    protected double getStarvationCoefficient() {
        return 1.3;
    }

    @Override
    protected int getChanceForReproduce() {
        return 45;
    }
}
