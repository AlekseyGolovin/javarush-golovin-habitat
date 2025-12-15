package ua.net.agsoft.javarush.habitat.config;

import ua.net.agsoft.javarush.habitat.entity.island.Island;
import ua.net.agsoft.javarush.habitat.entity.organism.Organism;
import ua.net.agsoft.javarush.habitat.entity.organism.OrganismParameter;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.herbivore.*;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.carnivore.*;
import ua.net.agsoft.javarush.habitat.entity.organism.plant.Plant;

import java.util.HashMap;
import java.util.Map;

public class DefaultConfigurator {

    private final Map<Class<? extends Organism>, OrganismParameter> organismParameterMap = new HashMap<>();
    private final Map<Class<? extends Organism>, Map<Class<? extends Organism>, Integer>> eatenProbabilitiesMap = new HashMap<>();

    public Island configureIsland() {
        return Island.instanceOf(3, 2);
    }

    public OrganismConfiguration configureOrganism() {
        OrganismConfiguration organismConfiguration = new OrganismConfiguration();
        fillOrganismParameterMap();
        fillEatenProbabilitiesMap();
        organismConfiguration.setOrganismParameterMap(organismParameterMap);
        organismConfiguration.setEatenProbabilitiesMap(eatenProbabilitiesMap);
        return organismConfiguration;
    }

    private void fillOrganismParameterMap() {
        organismParameterMap.put(Wolf.class, new OrganismParameter(50, 30, 3, 8));
        organismParameterMap.put(Boa.class, new OrganismParameter(15, 30, 1, 3));
        organismParameterMap.put(Fox.class, new OrganismParameter(8, 30, 2, 2));
        organismParameterMap.put(Bear.class, new OrganismParameter(500, 5, 2, 80));
        organismParameterMap.put(Eagle.class, new OrganismParameter(6, 20, 3, 1));
        organismParameterMap.put(Horse.class, new OrganismParameter(400, 20, 4, 60));
        organismParameterMap.put(Deer.class, new OrganismParameter(300, 20, 4, 50));
        organismParameterMap.put(Rabbit.class, new OrganismParameter(2, 150, 2, 0.45));
        organismParameterMap.put(Mouse.class, new OrganismParameter(0.05, 500, 1, 0.01));
        organismParameterMap.put(Goat.class, new OrganismParameter(60, 140, 3, 10));
        organismParameterMap.put(Sheep.class, new OrganismParameter(70, 140, 3, 15));
        organismParameterMap.put(Boar.class, new OrganismParameter(400, 50, 2, 50));
        organismParameterMap.put(Buffalo.class, new OrganismParameter(700, 10, 3, 100));
        organismParameterMap.put(Duck.class, new OrganismParameter(1, 200, 4, 0.15));
        organismParameterMap.put(Worm.class, new OrganismParameter(0.01, 1000, 0, 0));
        organismParameterMap.put(Plant.class, new OrganismParameter(1, 200, 0, 0));
    }

    private void fillEatenProbabilitiesMap() {
        fillDefaultWolfEatenProbabilitiesMap();
        fillDefaultBoaEatenProbabilitiesMap();
        fillDefaultFoxEatenProbabilitiesMap();
        fillDefaultBearEatenProbabilitiesMap();
        fillDefaultEagleEatenProbabilitiesMap();
        fillDefaultHorseEatenProbabilitiesMap();
        fillDefaultDeerEatenProbabilitiesMap();
        fillDefaultRabbitEatenProbabilitiesMap();
        fillDefaultMouseEatenProbabilitiesMap();
        fillDefaultGoatEatenProbabilitiesMap();
        fillDefaultSheepEatenProbabilitiesMap();
        fillDefaultBoarSheepEatenProbabilitiesMap();
        fillDefaultBuffaloEatenProbabilitiesMap();
        fillDefaultDuckEatenProbabilitiesMap();
        fillDefaultCaterpillarEatenProbabilitiesMap();
    }

    private void fillDefaultWolfEatenProbabilitiesMap() {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        itemEatenProbabilitiesMap.put(Wolf.class, 0);
        itemEatenProbabilitiesMap.put(Boa.class, 0);
        itemEatenProbabilitiesMap.put(Fox.class, 0);
        itemEatenProbabilitiesMap.put(Bear.class, 0);
        itemEatenProbabilitiesMap.put(Eagle.class, 0);
        itemEatenProbabilitiesMap.put(Horse.class, 10);
        itemEatenProbabilitiesMap.put(Deer.class, 15);
        itemEatenProbabilitiesMap.put(Rabbit.class, 60);
        itemEatenProbabilitiesMap.put(Mouse.class, 80);
        itemEatenProbabilitiesMap.put(Goat.class, 60);
        itemEatenProbabilitiesMap.put(Sheep.class, 70);
        itemEatenProbabilitiesMap.put(Boar.class, 15);
        itemEatenProbabilitiesMap.put(Buffalo.class, 10);
        itemEatenProbabilitiesMap.put(Duck.class, 40);
        itemEatenProbabilitiesMap.put(Worm.class, 0);
        itemEatenProbabilitiesMap.put(Plant.class, 0);
        eatenProbabilitiesMap.put(Wolf.class, itemEatenProbabilitiesMap);
    }

    private void fillDefaultBoaEatenProbabilitiesMap() {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        itemEatenProbabilitiesMap.put(Wolf.class, 0);
        itemEatenProbabilitiesMap.put(Boa.class, 0);
        itemEatenProbabilitiesMap.put(Fox.class, 15);
        itemEatenProbabilitiesMap.put(Bear.class, 0);
        itemEatenProbabilitiesMap.put(Eagle.class, 0);
        itemEatenProbabilitiesMap.put(Horse.class, 0);
        itemEatenProbabilitiesMap.put(Deer.class, 0);
        itemEatenProbabilitiesMap.put(Rabbit.class, 20);
        itemEatenProbabilitiesMap.put(Mouse.class, 40);
        itemEatenProbabilitiesMap.put(Goat.class, 0);
        itemEatenProbabilitiesMap.put(Sheep.class, 0);
        itemEatenProbabilitiesMap.put(Boar.class, 0);
        itemEatenProbabilitiesMap.put(Buffalo.class, 0);
        itemEatenProbabilitiesMap.put(Duck.class, 10);
        itemEatenProbabilitiesMap.put(Worm.class, 0);
        itemEatenProbabilitiesMap.put(Plant.class, 0);
        eatenProbabilitiesMap.put(Boa.class, itemEatenProbabilitiesMap);
    }

    private void fillDefaultFoxEatenProbabilitiesMap() {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        itemEatenProbabilitiesMap.put(Wolf.class, 0);
        itemEatenProbabilitiesMap.put(Boa.class, 0);
        itemEatenProbabilitiesMap.put(Fox.class, 0);
        itemEatenProbabilitiesMap.put(Bear.class, 0);
        itemEatenProbabilitiesMap.put(Eagle.class, 0);
        itemEatenProbabilitiesMap.put(Horse.class, 0);
        itemEatenProbabilitiesMap.put(Deer.class, 0);
        itemEatenProbabilitiesMap.put(Rabbit.class, 70);
        itemEatenProbabilitiesMap.put(Mouse.class, 90);
        itemEatenProbabilitiesMap.put(Goat.class, 0);
        itemEatenProbabilitiesMap.put(Sheep.class, 0);
        itemEatenProbabilitiesMap.put(Boar.class, 0);
        itemEatenProbabilitiesMap.put(Buffalo.class, 0);
        itemEatenProbabilitiesMap.put(Duck.class, 60);
        itemEatenProbabilitiesMap.put(Worm.class, 40);
        itemEatenProbabilitiesMap.put(Plant.class, 0);
        eatenProbabilitiesMap.put(Fox.class, itemEatenProbabilitiesMap);
    }

    private void fillDefaultBearEatenProbabilitiesMap() {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        itemEatenProbabilitiesMap.put(Wolf.class, 0);
        itemEatenProbabilitiesMap.put(Boa.class, 80);
        itemEatenProbabilitiesMap.put(Fox.class, 0);
        itemEatenProbabilitiesMap.put(Bear.class, 0);
        itemEatenProbabilitiesMap.put(Eagle.class, 0);
        itemEatenProbabilitiesMap.put(Horse.class, 40);
        itemEatenProbabilitiesMap.put(Deer.class, 80);
        itemEatenProbabilitiesMap.put(Rabbit.class, 80);
        itemEatenProbabilitiesMap.put(Mouse.class, 90);
        itemEatenProbabilitiesMap.put(Goat.class, 70);
        itemEatenProbabilitiesMap.put(Sheep.class, 70);
        itemEatenProbabilitiesMap.put(Boar.class, 50);
        itemEatenProbabilitiesMap.put(Buffalo.class, 20);
        itemEatenProbabilitiesMap.put(Duck.class, 10);
        itemEatenProbabilitiesMap.put(Worm.class, 0);
        itemEatenProbabilitiesMap.put(Plant.class, 0);
        eatenProbabilitiesMap.put(Bear.class, itemEatenProbabilitiesMap);
    }

    private void fillDefaultEagleEatenProbabilitiesMap() {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        itemEatenProbabilitiesMap.put(Wolf.class, 0);
        itemEatenProbabilitiesMap.put(Boa.class, 0);
        itemEatenProbabilitiesMap.put(Fox.class, 10);
        itemEatenProbabilitiesMap.put(Bear.class, 0);
        itemEatenProbabilitiesMap.put(Eagle.class, 0);
        itemEatenProbabilitiesMap.put(Horse.class, 0);
        itemEatenProbabilitiesMap.put(Deer.class, 0);
        itemEatenProbabilitiesMap.put(Rabbit.class, 90);
        itemEatenProbabilitiesMap.put(Mouse.class, 90);
        itemEatenProbabilitiesMap.put(Goat.class, 0);
        itemEatenProbabilitiesMap.put(Sheep.class, 0);
        itemEatenProbabilitiesMap.put(Boar.class, 0);
        itemEatenProbabilitiesMap.put(Buffalo.class, 0);
        itemEatenProbabilitiesMap.put(Duck.class, 80);
        itemEatenProbabilitiesMap.put(Worm.class, 0);
        itemEatenProbabilitiesMap.put(Plant.class, 0);
        eatenProbabilitiesMap.put(Eagle.class, itemEatenProbabilitiesMap);
    }

    private void fillDefaultHorseEatenProbabilitiesMap() {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        itemEatenProbabilitiesMap.put(Wolf.class, 0);
        itemEatenProbabilitiesMap.put(Boa.class, 0);
        itemEatenProbabilitiesMap.put(Fox.class, 0);
        itemEatenProbabilitiesMap.put(Bear.class, 0);
        itemEatenProbabilitiesMap.put(Eagle.class, 0);
        itemEatenProbabilitiesMap.put(Horse.class, 0);
        itemEatenProbabilitiesMap.put(Deer.class, 0);
        itemEatenProbabilitiesMap.put(Rabbit.class, 0);
        itemEatenProbabilitiesMap.put(Mouse.class, 0);
        itemEatenProbabilitiesMap.put(Goat.class, 0);
        itemEatenProbabilitiesMap.put(Sheep.class, 0);
        itemEatenProbabilitiesMap.put(Boar.class, 0);
        itemEatenProbabilitiesMap.put(Buffalo.class, 0);
        itemEatenProbabilitiesMap.put(Duck.class, 0);
        itemEatenProbabilitiesMap.put(Worm.class, 0);
        itemEatenProbabilitiesMap.put(Plant.class, 100);
        eatenProbabilitiesMap.put(Horse.class, itemEatenProbabilitiesMap);
    }

    private void fillDefaultDeerEatenProbabilitiesMap() {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        itemEatenProbabilitiesMap.put(Wolf.class, 0);
        itemEatenProbabilitiesMap.put(Boa.class, 0);
        itemEatenProbabilitiesMap.put(Fox.class, 0);
        itemEatenProbabilitiesMap.put(Bear.class, 0);
        itemEatenProbabilitiesMap.put(Eagle.class, 0);
        itemEatenProbabilitiesMap.put(Horse.class, 0);
        itemEatenProbabilitiesMap.put(Deer.class, 0);
        itemEatenProbabilitiesMap.put(Rabbit.class, 0);
        itemEatenProbabilitiesMap.put(Mouse.class, 0);
        itemEatenProbabilitiesMap.put(Goat.class, 0);
        itemEatenProbabilitiesMap.put(Sheep.class, 0);
        itemEatenProbabilitiesMap.put(Boar.class, 0);
        itemEatenProbabilitiesMap.put(Buffalo.class, 0);
        itemEatenProbabilitiesMap.put(Duck.class, 0);
        itemEatenProbabilitiesMap.put(Worm.class, 0);
        itemEatenProbabilitiesMap.put(Plant.class, 100);
        eatenProbabilitiesMap.put(Deer.class, itemEatenProbabilitiesMap);
    }

    private void fillDefaultRabbitEatenProbabilitiesMap() {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        itemEatenProbabilitiesMap.put(Wolf.class, 0);
        itemEatenProbabilitiesMap.put(Boa.class, 0);
        itemEatenProbabilitiesMap.put(Fox.class, 0);
        itemEatenProbabilitiesMap.put(Bear.class, 0);
        itemEatenProbabilitiesMap.put(Eagle.class, 0);
        itemEatenProbabilitiesMap.put(Horse.class, 0);
        itemEatenProbabilitiesMap.put(Deer.class, 0);
        itemEatenProbabilitiesMap.put(Rabbit.class, 0);
        itemEatenProbabilitiesMap.put(Mouse.class, 0);
        itemEatenProbabilitiesMap.put(Goat.class, 0);
        itemEatenProbabilitiesMap.put(Sheep.class, 0);
        itemEatenProbabilitiesMap.put(Boar.class, 0);
        itemEatenProbabilitiesMap.put(Buffalo.class, 0);
        itemEatenProbabilitiesMap.put(Duck.class, 0);
        itemEatenProbabilitiesMap.put(Worm.class, 0);
        itemEatenProbabilitiesMap.put(Plant.class, 100);
        eatenProbabilitiesMap.put(Rabbit.class, itemEatenProbabilitiesMap);
    }

    private void fillDefaultMouseEatenProbabilitiesMap() {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        itemEatenProbabilitiesMap.put(Wolf.class, 0);
        itemEatenProbabilitiesMap.put(Boa.class, 0);
        itemEatenProbabilitiesMap.put(Fox.class, 0);
        itemEatenProbabilitiesMap.put(Bear.class, 0);
        itemEatenProbabilitiesMap.put(Eagle.class, 0);
        itemEatenProbabilitiesMap.put(Horse.class, 0);
        itemEatenProbabilitiesMap.put(Deer.class, 0);
        itemEatenProbabilitiesMap.put(Rabbit.class, 0);
        itemEatenProbabilitiesMap.put(Mouse.class, 0);
        itemEatenProbabilitiesMap.put(Goat.class, 0);
        itemEatenProbabilitiesMap.put(Sheep.class, 0);
        itemEatenProbabilitiesMap.put(Boar.class, 0);
        itemEatenProbabilitiesMap.put(Buffalo.class, 0);
        itemEatenProbabilitiesMap.put(Duck.class, 0);
        itemEatenProbabilitiesMap.put(Worm.class, 90);
        itemEatenProbabilitiesMap.put(Plant.class, 100);
        eatenProbabilitiesMap.put(Mouse.class, itemEatenProbabilitiesMap);
    }

    private void fillDefaultGoatEatenProbabilitiesMap() {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        itemEatenProbabilitiesMap.put(Wolf.class, 0);
        itemEatenProbabilitiesMap.put(Boa.class, 0);
        itemEatenProbabilitiesMap.put(Fox.class, 0);
        itemEatenProbabilitiesMap.put(Bear.class, 0);
        itemEatenProbabilitiesMap.put(Eagle.class, 0);
        itemEatenProbabilitiesMap.put(Horse.class, 0);
        itemEatenProbabilitiesMap.put(Deer.class, 0);
        itemEatenProbabilitiesMap.put(Rabbit.class, 0);
        itemEatenProbabilitiesMap.put(Mouse.class, 0);
        itemEatenProbabilitiesMap.put(Goat.class, 0);
        itemEatenProbabilitiesMap.put(Sheep.class, 0);
        itemEatenProbabilitiesMap.put(Boar.class, 0);
        itemEatenProbabilitiesMap.put(Buffalo.class, 0);
        itemEatenProbabilitiesMap.put(Duck.class, 0);
        itemEatenProbabilitiesMap.put(Worm.class, 0);
        itemEatenProbabilitiesMap.put(Plant.class, 100);
        eatenProbabilitiesMap.put(Goat.class, itemEatenProbabilitiesMap);
    }

    private void fillDefaultSheepEatenProbabilitiesMap() {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        itemEatenProbabilitiesMap.put(Wolf.class, 0);
        itemEatenProbabilitiesMap.put(Boa.class, 0);
        itemEatenProbabilitiesMap.put(Fox.class, 0);
        itemEatenProbabilitiesMap.put(Bear.class, 0);
        itemEatenProbabilitiesMap.put(Eagle.class, 0);
        itemEatenProbabilitiesMap.put(Horse.class, 0);
        itemEatenProbabilitiesMap.put(Deer.class, 0);
        itemEatenProbabilitiesMap.put(Rabbit.class, 0);
        itemEatenProbabilitiesMap.put(Mouse.class, 0);
        itemEatenProbabilitiesMap.put(Goat.class, 0);
        itemEatenProbabilitiesMap.put(Sheep.class, 0);
        itemEatenProbabilitiesMap.put(Boar.class, 0);
        itemEatenProbabilitiesMap.put(Buffalo.class, 0);
        itemEatenProbabilitiesMap.put(Duck.class, 0);
        itemEatenProbabilitiesMap.put(Worm.class, 0);
        itemEatenProbabilitiesMap.put(Plant.class, 100);
        eatenProbabilitiesMap.put(Sheep.class, itemEatenProbabilitiesMap);
    }

    private void fillDefaultBoarSheepEatenProbabilitiesMap() {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        itemEatenProbabilitiesMap.put(Wolf.class, 0);
        itemEatenProbabilitiesMap.put(Boa.class, 0);
        itemEatenProbabilitiesMap.put(Fox.class, 0);
        itemEatenProbabilitiesMap.put(Bear.class, 0);
        itemEatenProbabilitiesMap.put(Eagle.class, 0);
        itemEatenProbabilitiesMap.put(Horse.class, 0);
        itemEatenProbabilitiesMap.put(Deer.class, 0);
        itemEatenProbabilitiesMap.put(Rabbit.class, 0);
        itemEatenProbabilitiesMap.put(Mouse.class, 50);
        itemEatenProbabilitiesMap.put(Goat.class, 0);
        itemEatenProbabilitiesMap.put(Sheep.class, 0);
        itemEatenProbabilitiesMap.put(Boar.class, 0);
        itemEatenProbabilitiesMap.put(Buffalo.class, 0);
        itemEatenProbabilitiesMap.put(Duck.class, 0);
        itemEatenProbabilitiesMap.put(Worm.class, 90);
        itemEatenProbabilitiesMap.put(Plant.class, 100);
        eatenProbabilitiesMap.put(Boar.class, itemEatenProbabilitiesMap);
    }

    private void fillDefaultBuffaloEatenProbabilitiesMap() {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        itemEatenProbabilitiesMap.put(Wolf.class, 0);
        itemEatenProbabilitiesMap.put(Boa.class, 0);
        itemEatenProbabilitiesMap.put(Fox.class, 0);
        itemEatenProbabilitiesMap.put(Bear.class, 0);
        itemEatenProbabilitiesMap.put(Eagle.class, 0);
        itemEatenProbabilitiesMap.put(Horse.class, 0);
        itemEatenProbabilitiesMap.put(Deer.class, 0);
        itemEatenProbabilitiesMap.put(Rabbit.class, 0);
        itemEatenProbabilitiesMap.put(Mouse.class, 0);
        itemEatenProbabilitiesMap.put(Goat.class, 0);
        itemEatenProbabilitiesMap.put(Sheep.class, 0);
        itemEatenProbabilitiesMap.put(Boar.class, 0);
        itemEatenProbabilitiesMap.put(Buffalo.class, 0);
        itemEatenProbabilitiesMap.put(Duck.class, 0);
        itemEatenProbabilitiesMap.put(Worm.class, 0);
        itemEatenProbabilitiesMap.put(Plant.class, 100);
        eatenProbabilitiesMap.put(Buffalo.class, itemEatenProbabilitiesMap);
    }

    private void fillDefaultDuckEatenProbabilitiesMap() {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        itemEatenProbabilitiesMap.put(Wolf.class, 0);
        itemEatenProbabilitiesMap.put(Boa.class, 0);
        itemEatenProbabilitiesMap.put(Fox.class, 0);
        itemEatenProbabilitiesMap.put(Bear.class, 0);
        itemEatenProbabilitiesMap.put(Eagle.class, 0);
        itemEatenProbabilitiesMap.put(Horse.class, 0);
        itemEatenProbabilitiesMap.put(Deer.class, 0);
        itemEatenProbabilitiesMap.put(Rabbit.class, 0);
        itemEatenProbabilitiesMap.put(Mouse.class, 0);
        itemEatenProbabilitiesMap.put(Goat.class, 0);
        itemEatenProbabilitiesMap.put(Sheep.class, 0);
        itemEatenProbabilitiesMap.put(Boar.class, 0);
        itemEatenProbabilitiesMap.put(Buffalo.class, 0);
        itemEatenProbabilitiesMap.put(Duck.class, 0);
        itemEatenProbabilitiesMap.put(Worm.class, 90);
        itemEatenProbabilitiesMap.put(Plant.class, 100);
        eatenProbabilitiesMap.put(Duck.class, itemEatenProbabilitiesMap);
    }

    private void fillDefaultCaterpillarEatenProbabilitiesMap() {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        itemEatenProbabilitiesMap.put(Wolf.class, 0);
        itemEatenProbabilitiesMap.put(Boa.class, 0);
        itemEatenProbabilitiesMap.put(Fox.class, 0);
        itemEatenProbabilitiesMap.put(Bear.class, 0);
        itemEatenProbabilitiesMap.put(Eagle.class, 0);
        itemEatenProbabilitiesMap.put(Horse.class, 0);
        itemEatenProbabilitiesMap.put(Deer.class, 0);
        itemEatenProbabilitiesMap.put(Rabbit.class, 0);
        itemEatenProbabilitiesMap.put(Mouse.class, 0);
        itemEatenProbabilitiesMap.put(Goat.class, 0);
        itemEatenProbabilitiesMap.put(Sheep.class, 0);
        itemEatenProbabilitiesMap.put(Boar.class, 0);
        itemEatenProbabilitiesMap.put(Buffalo.class, 0);
        itemEatenProbabilitiesMap.put(Duck.class, 0);
        itemEatenProbabilitiesMap.put(Worm.class, 0);
        itemEatenProbabilitiesMap.put(Plant.class, 100);
        eatenProbabilitiesMap.put(Worm.class, itemEatenProbabilitiesMap);
    }
}
