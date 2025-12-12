package ua.net.agsoft.javarush.habitat.entity.organism.animal.carnivore;

import ua.net.agsoft.javarush.habitat.entity.organism.animal.Animal;

public abstract class Carnivore extends Animal {

    public Carnivore() {
    }

    protected boolean canEatPlants(){
        return false;
    }
}
