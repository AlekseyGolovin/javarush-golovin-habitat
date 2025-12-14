package ua.net.agsoft.javarush.habitat.statistic;

import ua.net.agsoft.javarush.habitat.entity.island.Island;

public abstract class Statistic {

    public abstract void show(int beat);

    public abstract void showExtinction();

    public abstract void setShowAnimal(boolean showAnimal);

    public abstract void setIsland(Island island);

    public abstract void updateRandomAnimal();
}
