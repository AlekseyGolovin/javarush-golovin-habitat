package ua.net.agsoft.javarush.habitat.entity.island;

import ua.net.agsoft.javarush.habitat.entity.organism.Organism;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.Animal;
import ua.net.agsoft.javarush.habitat.entity.organism.plant.Plant;

import java.util.HashMap;
import java.util.Map;

public class Cell {

    private static Map<Class<? extends Organism>, Integer> organismMaxCountMap;

    private int plantCount = 0;
    private Map<Class<? extends Animal>, Integer> animalCountMap = new HashMap<>();


    public static void setOrganismMaxCountMap(Map<Class<? extends Organism>, Integer> organismMaxCountMap) {
        Cell.organismMaxCountMap = organismMaxCountMap;
    }

    public void setPlantCount(int plantCount) {
        int maxCount = organismMaxCountMap.getOrDefault(Plant.class, 0);
        if (plantCount < 0) {
            this.plantCount = 0;
            return;
        }
        if (plantCount > maxCount) {
            this.plantCount = maxCount;
            return;
        }
        this.plantCount = plantCount;
    }

    public boolean incPlant() {
        int maxCount = organismMaxCountMap.getOrDefault(Plant.class, 0);
        if (plantCount < maxCount) {
            plantCount++;
            return true;
        }
        return false;
    }

    public void decPlant() {
        if (plantCount > 0) {
            plantCount--;
            return;
        }
        throw new RuntimeException("Invalid change of " + Plant.class.getSimpleName() + " type quantity");
    }













    public boolean incAnimal(Animal animal) {
        Class<? extends Animal> animalClazz = animal.getClass();
        int maxCount = organismMaxCountMap.getOrDefault(animalClazz, 0);
        int curCount = animalCountMap.getOrDefault(animalClazz, 0);
        if (curCount < maxCount) {
            curCount++;
            animalCountMap.put(animalClazz, curCount);
            return true;
        }
        return false;
    }

    public void decAnimal(Animal animal) {
        Class<? extends Animal> animalClazz = animal.getClass();
        //int maxCount = organismMaxCountMap.getOrDefault(animalClazz, 0);
        int curCount = animalCountMap.getOrDefault(animalClazz, 0);
        if (curCount > 0) {
            curCount--;
            animalCountMap.put(animalClazz, curCount);
            return;
        }
        throw new RuntimeException("Invalid change of " + animalClazz.getSimpleName() + " type quantity");
    }
}
