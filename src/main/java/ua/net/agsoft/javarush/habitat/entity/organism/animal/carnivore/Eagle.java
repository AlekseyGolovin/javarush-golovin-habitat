package ua.net.agsoft.javarush.habitat.entity.organism.animal.carnivore;

public class Eagle extends Carnivore {

    private static int num = 0;

    private String name;

    public Eagle() {
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
        return 8.0;
    }

    @Override
    protected int getChanceForReproduce() {
        return 60;
    }
}
