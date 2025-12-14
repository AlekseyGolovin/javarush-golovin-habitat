package ua.net.agsoft.javarush.habitat.entity.organism.animal.herbivore;

import ua.net.agsoft.javarush.habitat.entity.organism.animal.Animal;

public abstract class Herbivore extends Animal {

    public boolean canEatPlants() {
        return true;
    }
}
