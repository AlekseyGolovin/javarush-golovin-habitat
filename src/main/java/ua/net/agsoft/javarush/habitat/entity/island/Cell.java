package ua.net.agsoft.javarush.habitat.entity.island;

import ua.net.agsoft.javarush.habitat.entity.organism.Organism;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.Animal;
import ua.net.agsoft.javarush.habitat.entity.organism.plant.Plant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Cell {

    private static Map<Class<? extends Organism>, Integer> organismMaxCountMap;
    // TODO: Используя список животных в ячейке обработка будет проходить значительно быстрее,
    //  особенно на больших размерах острова
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final Map<Class<? extends Animal>, Integer> animalCountMap = new HashMap<>();
    // Тут храним количество организмов
    // Растение дробным числом для озможности мелким животным есть малую часть
    private double plantCount = 0;

    public static void setOrganismMaxCountMap(Map<Class<? extends Organism>, Integer> organismMaxCountMap) {
        Cell.organismMaxCountMap = organismMaxCountMap;
    }

    public void addPlants(double count) {
        int maxCount = organismMaxCountMap.getOrDefault(Plant.class, 0);
        plantCount += count;
        if (plantCount > maxCount) {
            plantCount = maxCount;
        }
    }

    public void decPlant(double dec) {
        if (plantCount > 0) {
            plantCount -= dec;
            return;
        }
        throw new RuntimeException("Invalid change of " + Plant.class.getSimpleName() + " type quantity");
    }

    private boolean canAddAnimal(Class<? extends Animal> animalClazz) {
        int maxCount = organismMaxCountMap.getOrDefault(animalClazz, 0);
        int curCount = animalCountMap.getOrDefault(animalClazz, 0);
        return curCount < maxCount;
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

    public double getPlantCount() {
        return plantCount;
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


    public void growPlants() {
        addPlants(Plant.getGrowCount(plantCount));

    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }
}