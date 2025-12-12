package ua.net.agsoft.javarush.habitat.entity.island;

import ua.net.agsoft.javarush.habitat.config.OrganismConfiguration;
import ua.net.agsoft.javarush.habitat.entity.organism.Organism;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.Animal;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.AnimalType;
import ua.net.agsoft.javarush.habitat.entity.organism.plant.Plant;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Island {

    private static volatile Island instance;

    private int startPercentPopulation = 30;
    private OrganismConfiguration organismConfiguration;

    ArrayList<Animal> animals = new ArrayList<>();

    private int width;
    private int height;
    private Cell[][] cells;

    private Island(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        System.out.printf("Island initialized: Size [%d x %d]\n", width, height);
    }

    public static Island instanceOf(int width, int height) {
        if (instance == null) {
            instance = new Island(width, height);
        }
        return instance;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void setStartPercentPopulation(int startPercentPopulation) {
        this.startPercentPopulation = startPercentPopulation;
    }

    public void populate(OrganismConfiguration organismConfiguration) {
        setOrganismConfiguration(organismConfiguration);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                fillCell(x, y);
            }
        }
    }

    private void setOrganismConfiguration(OrganismConfiguration organismConfiguration) {
        this.organismConfiguration = organismConfiguration;
        Map <Class<? extends Organism>, Integer> maxOrganismMap = new HashMap<>();
        for (AnimalType animalType : AnimalType.values()) {
            int maxCountPerCell = organismConfiguration.getOrganismParameterMap().get(animalType.getAnimalClass()).getMaxCountPerCell();
            maxOrganismMap.put(animalType.getAnimalClass(), maxCountPerCell);
        }
        Cell.setOrganismMaxCountMap(maxOrganismMap);
    }

    private void fillCell(int x, int y) {
        Cell cell = cells[x][y];

        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        int maxPlantCountPerCell = organismConfiguration.getOrganismParameterMap().get(Plant.class).getMaxCountPerCell();
        int plantCount = tlr.nextInt(maxPlantCountPerCell * startPercentPopulation / 100);
        cell.setPlantCount(plantCount);

        for (AnimalType animalType : AnimalType.values()) {
            Class<? extends Animal> clazz = animalType.getAnimalClass();

            int maxCountPerCell = organismConfiguration.getOrganismParameterMap().get(clazz).getMaxCountPerCell();
            double foodRequired = organismConfiguration.getOrganismParameterMap().get(clazz).getFoodRequired();
            int animalCount = tlr.nextInt(maxCountPerCell * startPercentPopulation / 100);
            for (int i = 0; i < animalCount; i++) {
                // Создать животное
                Animal animal = Animal.createInstance(animalType.getAnimalClass());
                animal.setMaxSaturation(foodRequired);
                animal.setSaturation(foodRequired / 2);
                animal.setPositionX(x);
                animal.setPositionY(y);

                // Привязать к ячейке
                if(!cell.incAnimal(animal)) {
                    throw new RuntimeException(clazz.getSimpleName() + " type overflow when creating an island");
                }

                animals.add(animal);



            }
        }
    }
}
