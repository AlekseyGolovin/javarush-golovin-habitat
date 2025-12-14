package ua.net.agsoft.javarush.habitat.entity.organism.animal.carnivore;

public class Fox extends Carnivore {

    private static int num = 0;

    private String name;

    public Fox() {
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
        return 2.0;
    }

    @Override
    protected int getChanceForReproduce() {
        return 65;
    }
}
