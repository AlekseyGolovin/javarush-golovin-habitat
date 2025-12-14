package ua.net.agsoft.javarush.habitat.entity.island;

import ua.net.agsoft.javarush.habitat.config.OrganismConfiguration;
import ua.net.agsoft.javarush.habitat.entity.organism.Organism;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.Animal;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.AnimalType;
import ua.net.agsoft.javarush.habitat.entity.organism.plant.Plant;
import ua.net.agsoft.javarush.habitat.util.Util;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Island {

    private static volatile Island instance;
    private final StringBuilder randomAnimalActions = new StringBuilder();
    private Class<? extends Animal> randomAnimalClass;
    private Animal randomAnimal;
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

    public void selectRandomAnimal() {
        List<Animal> animalList;
        if (randomAnimalClass != null) {
            animalList = animals.stream()
                    .filter(a -> a.getClass() == randomAnimalClass)
                    .filter(Animal::isAlive)
                    .toList();
        } else {
            animalList = animals;
        }

        randomAnimal = Util.getRandom(animalList);
        if (randomAnimalClass == null && randomAnimal != null) {
            randomAnimalClass = randomAnimal.getClass();
        }
    }

    public void addRandomAnimalAction(String action) {
        randomAnimalActions.append(action);
    }

    public String getRandomAnimalActions() {
        return randomAnimalActions.toString();
    }

    private int getAnimalCountFromCells(Class<? extends Animal> animalClazz) {
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

    public int getAnimalCount() {
        return animals.size();
    }

    public int getAnimalCount(Class<? extends Animal> animalClazz) {
        return getAnimalCountFromList(animalClazz);
    }

    public int getPlantCount() {
        double sum = 0.0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = getCell(x, y);
                double count = cell.getPlantCount();
                sum += count;
            }
        }
        return (int) sum;
    }

    private int getAnimalCountFromList(Class<? extends Animal> animalClazz) {
        List<Animal> animalList = animals.stream().filter(a -> a.getClass() == animalClazz).toList();
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

    public void populate(OrganismConfiguration organismConfiguration) {
        setOrganismConfiguration(organismConfiguration);

        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                fillCell(tlr, x, y);
            }
        }


    }

    private void fillCell(ThreadLocalRandom tlr, int x, int y) {
        Cell cell = cells[x][y];

        int maxPlantCountPerCell = organismConfiguration.getOrganismParameterMap().get(Plant.class).getMaxCountPerCell();
        System.out.println("maxPlantCountPerCell: " + maxPlantCountPerCell);
        int plantCount = maxPlantCountPerCell / 2 + tlr.nextInt(maxPlantCountPerCell / 2);
        System.out.println("plantCount: " + plantCount);
        cell.setPlantCount(plantCount);

        for (AnimalType animalType : AnimalType.values()) {
            fillAnimalInCell(x, y, animalType, tlr, cell);
        }
    }

    private void fillAnimalInCell(int x, int y, AnimalType animalType, ThreadLocalRandom tlr, Cell cell) {

        Class<? extends Animal> clazz = animalType.getAnimalClass();

        int maxCountPerCell = organismConfiguration.getOrganismParameterMap().get(clazz).getMaxCountPerCell();

        //System.out.println(clazz.getSimpleName() + " maxCountPerCel: " + maxCountPerCell);

        double foodRequired = organismConfiguration.getOrganismParameterMap().get(clazz).getFoodRequired();
        double weight = organismConfiguration.getOrganismParameterMap().get(clazz).getWeight();
        int bound = maxCountPerCell * 10 / 100;
        if (bound < 1) {
            bound = 1;
        }
        int animalCount = (maxCountPerCell * (startPercentPopulation - 10) / 100) + (tlr.nextInt(bound));
        if (animalCount > maxCountPerCell) {
            animalCount = maxCountPerCell;
        }
        for (int i = 0; i < animalCount; i++) {

            Animal animal = Animal.createInstance(animalType.getAnimalClass());
            animal.setWeight(weight);
            animal.setMaxSaturation(foodRequired);
            animal.setSaturation(foodRequired / 2);
            animal.setPositionX(x);
            animal.setPositionY(y);

            if (!cell.incAnimal(animal)) {
                throw new RuntimeException(clazz.getSimpleName() + " type overflow when creating an island");
            }

            animals.add(animal);
        }
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

    public OrganismConfiguration getOrganismConfiguration() {
        return organismConfiguration;
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

    public Animal getPair(Animal animal) {
        int x = animal.getPositionX();
        int y = animal.getPositionY();
        Cell cell = getCell(x, y);
        if (!cell.canAddAnimal(animal)) {
            return null;
        }
        Class<? extends Animal> animalClazz = animal.getClass();
        List<Animal> pairList = animals.stream()
                .filter(a -> a.getClass() == animalClazz)
                .filter(a -> a.getPositionX() == x)
                .filter(a -> a.getPositionY() == y)
                .filter(Animal::isAlive)
                .filter(Animal::canAction)
                .toList();
        if (pairList.isEmpty()) {
            return null;
        }
        return pairList.getFirst();
    }

    public Animal getFood(Animal animal) {
        Class<? extends Animal> animalClazz = animal.getClass();

        Set<Class<? extends Animal>> foodClassSet = new HashSet<>();
        for (AnimalType animalType : AnimalType.values()) {
            Class<? extends Animal> foodClazz = animalType.getAnimalClass();
            int eatChance = organismConfiguration.getEatenProbabilitiesMap().get(animalClazz).get(foodClazz);
            if (eatChance > 0) {
                foodClassSet.add(foodClazz);
            }
        }
        if (foodClassSet.isEmpty()) {
            return null;
        }

        int x = animal.getPositionX();
        int y = animal.getPositionY();

        List<Animal> foodList = animals.stream()
                .filter(a -> a.getClass() != animalClazz)
                .filter(a -> a.getPositionX() == x)
                .filter(a -> a.getPositionY() == y)
                .filter(Animal::isAlive)
                .filter(a -> foodClassSet.contains(a.getClass()))
                .sorted(Comparator.comparingDouble(Animal::getWeight).reversed())
                .toList();
        return Util.getRandom(foodList);

//        if (foodList.isEmpty()) {
//            return null;
//        }
//        return foodList.getFirst();
    }

    public Class<? extends Animal> getRandomAnimalClass() {
        return randomAnimalClass;
    }

    public void setRandomAnimalClass(Class<? extends Animal> randomAnimalClass) {
        this.randomAnimalClass = randomAnimalClass;
    }

    public Animal getRandomAnimal() {
        return randomAnimal;
    }

    public void clearRandomAnimalAction() {
        randomAnimalActions.setLength(0);
    }
}
