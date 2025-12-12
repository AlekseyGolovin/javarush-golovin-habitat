package ua.net.agsoft.javarush.habitat.entity.island;

import ua.net.agsoft.javarush.habitat.entity.organism.Organism;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.Animal;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.AnimalType;
import ua.net.agsoft.javarush.habitat.entity.organism.plant.Plant;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Cell {

    private static int CHANCE_FOR_PLANTS_GROW = 10;
    private static int PLANTS_FERTILITY = 10;

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

    public void addPlants(int count) {
        int maxCount = organismMaxCountMap.getOrDefault(Plant.class, 0);
        plantCount += count;
        if (plantCount > maxCount) {
            plantCount = maxCount;
        }
    }

    public void decPlant() {
        if (plantCount > 0) {
            plantCount--;
            return;
        }
        throw new RuntimeException("Invalid change of " + Plant.class.getSimpleName() + " type quantity");
    }

    private boolean canAddAnimal(Class<? extends Animal> animalClazz) {
        int maxCount = organismMaxCountMap.getOrDefault(animalClazz, 0);
        int curCount = animalCountMap.getOrDefault(animalClazz, 0);
        if (curCount < maxCount) {
            return true;
        }
        return false;
    }

    public boolean canAddAnimal(Animal animal) {
        Class<? extends Animal> animalClazz = animal.getClass();
        return canAddAnimal(animalClazz);
    }

    public boolean incAnimal(Animal animal) {
        Class<? extends Animal> animalClazz = animal.getClass();
        if (canAddAnimal(animalClazz)) {

            int curCount = getAnimalCount(animalClazz);
            curCount++;
            animalCountMap.put(animalClazz, curCount);
            return true;
        }
        return false;
    }

    public int getAnimalCount(Class<? extends Animal> animalClazz) {
        return animalCountMap.getOrDefault(animalClazz, 0);
    }

    public int getPlantCount() {
        return plantCount;
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

    @Override
    public String toString() {

        StringBuilder animals = new StringBuilder();
        for (AnimalType animalType : AnimalType.values()) {
            Class<? extends Animal> animalClazz = animalType.getAnimalClass();
            int curCount = animalCountMap.getOrDefault(animalClazz, 0);
            int maxCount = organismMaxCountMap.getOrDefault(animalClazz, 0);
            String className = animalClazz.getSimpleName();
            animals.append(className).append(" ").append(curCount).append("/").append(maxCount).append(" ");
        }


        return "Cell {" +
                " plantCount=" + plantCount +
                ", animals [ " + animals + "] " +
                '}';
    }


    public void growPlants() {
        // C вероятностью 10% вырастит новая трава
        // +10% от текушего количества
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        int chance = tlr.nextInt(100);
        if (chance <= CHANCE_FOR_PLANTS_GROW) {
            incPlant();
        }
        addPlants(plantCount * PLANTS_FERTILITY / 100);
    }
}