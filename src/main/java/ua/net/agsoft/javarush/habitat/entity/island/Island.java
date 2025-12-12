package ua.net.agsoft.javarush.habitat.entity.island;

import ua.net.agsoft.javarush.habitat.config.OrganismConfiguration;
import ua.net.agsoft.javarush.habitat.entity.organism.Organism;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.Animal;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.AnimalType;
import ua.net.agsoft.javarush.habitat.entity.organism.plant.Plant;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Island {


    private static volatile Island instance;

    private int startPercentPopulation = 50;
    private OrganismConfiguration organismConfiguration;

    private ArrayList<Animal> animals = new ArrayList<>();

    private int width;
    private int height;
    private Cell[][] cells;

    private Island(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell();
            }
        }
        System.out.printf("Island initialized: Size [%d x %d]\n", width, height);
    }

    public static Island instanceOf(int width, int height) {
        if (instance == null) {
            instance = new Island(width, height);
        }
        return instance;
    }

    @Override
    public String toString() {
        // TODO: для теста показать кол-во волков на основе списка.
        //  Показать кол-во волков суммированием кол-ва по ячейкам
        String animalsStatistic = "";
        for (AnimalType animalType : AnimalType.values()) {
            Class<? extends Animal> animalClazz = animalType.getAnimalClass();
            int islandCount = getCountFromList(animalClazz);
            int cellsCount = getCountFromCells(animalClazz);
            String className = animalClazz.getSimpleName();
            String animalStatistic = className + ": [" + islandCount + "][" + cellsCount + "]\n";
            animalsStatistic += animalStatistic;
        }
        String answer = "Island:\n" +
                "width=" + width + " height=" + height + " \n" +
                "animals: " + animals.size() + "\n" +
                animalsStatistic;


        return answer;
    }

    private int getCountFromCells(Class<? extends Animal> animalClazz) {
        int sum = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = getCell(x, y);
                int count = cell.getAnimalCount(animalClazz);
                sum += count;
            }
        }
        return sum;
    }

    private int getCountFromList(Class<? extends Animal> animalClazz) {

        // Отфильтровать список по типу,
        List<Animal> animalList = animals.stream().filter(a -> a.getClass() == animalClazz).toList();

        // просуммировать
        return animalList.size();

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

    private void setOrganismConfiguration(OrganismConfiguration organismConfiguration) {
        this.organismConfiguration = organismConfiguration;
        Map<Class<? extends Organism>, Integer> maxOrganismMap = new HashMap<>();
        for (AnimalType animalType : AnimalType.values()) {
            Class<? extends Animal> animalClazz = animalType.getAnimalClass();
            int maxCountPerCell = organismConfiguration.getOrganismParameterMap().get(animalClazz).getMaxCountPerCell();
            maxOrganismMap.put(animalType.getAnimalClass(), maxCountPerCell);
        }
        int maxCountPerCell = organismConfiguration.getOrganismParameterMap().get(Plant.class).getMaxCountPerCell();
        maxOrganismMap.put(Plant.class, maxCountPerCell);

        Cell.setOrganismMaxCountMap(maxOrganismMap);
    }

    public void populate(OrganismConfiguration organismConfiguration) {
        setOrganismConfiguration(organismConfiguration);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                fillCell(x, y);
            }
        }
    }


    private void fillCell(int x, int y) {
        Cell cell = cells[x][y];

        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        int maxPlantCountPerCell = organismConfiguration.getOrganismParameterMap().get(Plant.class).getMaxCountPerCell();
        System.out.println("maxPlantCountPerCell: " + maxPlantCountPerCell);
        int plantCount = tlr.nextInt(maxPlantCountPerCell * startPercentPopulation / 100);
        System.out.println("plantCount: " + plantCount);
        cell.setPlantCount(plantCount);

        for (AnimalType animalType : AnimalType.values()) {
            Class<? extends Animal> clazz = animalType.getAnimalClass();

            int maxCountPerCell = organismConfiguration.getOrganismParameterMap().get(clazz).getMaxCountPerCell();
            double foodRequired = organismConfiguration.getOrganismParameterMap().get(clazz).getFoodRequired();
            double weight = organismConfiguration.getOrganismParameterMap().get(clazz).getWeight();
            int animalCount = tlr.nextInt(maxCountPerCell * startPercentPopulation / 100);
            for (int i = 0; i < animalCount; i++) {
                // Создать животное
                Animal animal = Animal.createInstance(animalType.getAnimalClass());
                animal.setWeight(weight);
                animal.setMaxSaturation(foodRequired);
                animal.setSaturation(foodRequired / 2);
                animal.setPositionX(x);
                animal.setPositionY(y);

                // Привязать к ячейке
                if (!cell.incAnimal(animal)) {
                    throw new RuntimeException(clazz.getSimpleName() + " type overflow when creating an island");
                }

                animals.add(animal);


            }
        }
    }

    public OrganismConfiguration getorganismConfiguration() {
        return organismConfiguration;
    }

    public boolean canMove(Animal animal, int x, int y) {
        if (x < 0) {
            return false;
        }
        if (y < 0) {
            return false;
        }
        if (x >= width) {
            return false;
        }
        if (y >= height) {
            return false;
        }
        return cells[x][y].canAddAnimal(animal);
    }

    public ArrayList<Animal> getAnimals() {
        Collections.shuffle(animals);
        return animals;
    }

    public void growPlants() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = getCell(x, y);
                cell.growPlants();
            }
        }
    }
}
