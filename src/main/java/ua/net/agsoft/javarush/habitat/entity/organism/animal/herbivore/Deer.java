package ua.net.agsoft.javarush.habitat.entity.organism.animal.herbivore;

public class Deer extends Herbivore {

    private static int num = 0;

    private String name;

    public Deer() {
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
        return 1.7;
    }

    @Override
    protected int getChanceForReproduce() {
        return 75;
    }
}
