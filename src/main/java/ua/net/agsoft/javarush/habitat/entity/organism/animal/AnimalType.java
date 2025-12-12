package ua.net.agsoft.javarush.habitat.entity.organism.animal;

import ua.net.agsoft.javarush.habitat.entity.organism.animal.herbivore.*;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.carnivore.*;

public enum AnimalType {
    WOLF(Wolf.class),
    BOA(Boa.class),
    FOX(Fox.class),
    BEAR(Bear.class),
    EAGLE(Eagle.class),
    HORSE(Horse.class),
    DEER(Deer.class),
    RABBIT(Rabbit.class),
    MOUSE(Mouse.class),
    GOAT(Goat.class),
    SHEEP(Sheep.class),
    BOAR(Boar.class),
    BUFFALO(Buffalo.class),
    DUCK(Duck.class),
    CATERPILLAR(Caterpillar.class);

    private final Class<? extends Animal> animalClass;

    AnimalType(Class<? extends Animal> animalClass) {
        this.animalClass = animalClass;
    }

    public Class<? extends Animal> getAnimalClass() {
        return animalClass;
    }
}
