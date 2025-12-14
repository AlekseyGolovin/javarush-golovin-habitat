package ua.net.agsoft.javarush.habitat.statistic;

import ua.net.agsoft.javarush.habitat.entity.island.Cell;
import ua.net.agsoft.javarush.habitat.entity.island.Island;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.Animal;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.AnimalType;

import java.util.HashMap;
import java.util.Map;

public class ConsoleStatistic extends Statistic {

    private static final String ANIMAL_ALIVE_INFO_FORMAT = "%s     Location: [%3d][%3d]      saturation: %3d%%     %s";
    private static final String ANIMAL_DEAD_INFO_FORMAT = "%s     Location: [%3d][%3d]      cause of death: %s";
    private static final String EXTINCTION_FORMAT = "The %s class became extinct after %d periods.";

    private static final String ROW_DELIMITER = "----------------------------------------------" +
            "----------------------------------------------------------------------------------" +
            "---------------------------------------------------------------------------------";
    private static final String BIG_COLUMN_DELIMITER = "     |     ";
    private static final String SMALL_COLUMN_DELIMITER = " | ";

    private final Map<Class<? extends Animal>, Integer> extinctionMap = new HashMap<>();
    Animal randomAnimal;
    String randomAnimalClassName;
    private boolean showAnimal = false;
    private Island island;
    private Class<? extends Animal> randomAnimalClass;

    private static void clearScreen() {
        try {
            String operatingSystem = System.getProperty("os.name");
            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void show(int beat) {
        //clearScreen();
        showInConsoleIslandSummary(beat);
        showInConsoleAnimalSummary(beat);
        if (showAnimal) showAnimalLocation();


        //System.out.println(ROW_DELIMITER);
        System.out.println();
        System.out.println();
        System.out.println();
    }

    @Override
    public void showExtinction() {
        for (var pair : extinctionMap.entrySet()) {
            String className = pair.getKey().getSimpleName();
            int extinctionDay = pair.getValue();
            System.out.println(String.format(EXTINCTION_FORMAT, className, extinctionDay));
        }
    }

    private void showAnimalLocation() {
        System.out.println(ROW_DELIMITER);
        System.out.println(randomAnimalClass.getSimpleName() + " location");
        System.out.println(ROW_DELIMITER);

        int width = island.getWidth();
        if (width > 15) {
            width = 15;
        }
        int height = island.getHeight();
        if (height > 15) {
            height = 15;
        }
        StringBuilder location = new StringBuilder();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = island.getCell(x, y);
                int count = cell.getAnimalCount(randomAnimalClass);
                location.append(String.format("%-11d", count)).append(SMALL_COLUMN_DELIMITER);

            }
            location.append("\n")
                    .append(ROW_DELIMITER).append("\n");
        }
        System.out.print(location);


    }


    private void showInConsoleIslandSummary(int beat) {
        String summary = getIslandSummary(beat);
        String randomAnimalInfo = getRandomAnimalInfo();
        System.out.println(ROW_DELIMITER);
        System.out.println(summary + BIG_COLUMN_DELIMITER + randomAnimalInfo);
    }

    private void showInConsoleAnimalSummary(int beat) {
        String animalSummaryHeader = getAnimalSummaryHeader();
        String animalSummaryInfo = getAnimalSummaryInfo();
        String animalSummaryPercent = getAnimalSummaryPercent(beat);
        System.out.println(ROW_DELIMITER);
        System.out.println(animalSummaryHeader);
        System.out.println(animalSummaryInfo);
        System.out.println(ROW_DELIMITER);
        System.out.println(animalSummaryPercent);
    }

    private String getAnimalSummaryPercent(int beat) {
        StringBuilder animalSummaryInfo = new StringBuilder();
        for (AnimalType animalType : AnimalType.values()) {
            Class<? extends Animal> animalClazz = animalType.getAnimalClass();
            int count = island.getAnimalCount(animalClazz);
            int maxCountPerCell = island.getOrganismConfiguration().getOrganismParameterMap().get(animalClazz).getMaxCountPerCell();
            int maxCount = maxCountPerCell * island.getWidth() * island.getHeight();
            animalSummaryInfo.append(String.format("%-10d%%", count * 100 / maxCount)).append(SMALL_COLUMN_DELIMITER);

            if (count == 0 && !extinctionMap.containsKey(animalClazz)) {
                extinctionMap.put(animalClazz, beat);
            }
        }
        return animalSummaryInfo.toString();


    }

    private String getAnimalSummaryMax() {
        StringBuilder animalSummaryInfo = new StringBuilder();
        for (AnimalType animalType : AnimalType.values()) {
            Class<? extends Animal> animalClazz = animalType.getAnimalClass();
            int maxCountPerCell = island.getOrganismConfiguration().getOrganismParameterMap().get(animalClazz).getMaxCountPerCell();
            int maxCount = maxCountPerCell * island.getWidth() * island.getHeight();
            animalSummaryInfo.append(String.format("%-11d", maxCount)).append(SMALL_COLUMN_DELIMITER);
        }
        return animalSummaryInfo.toString();


    }

    private String getIslandSummary(int beat) {
        return "beat " + String.format("%7d", beat) + BIG_COLUMN_DELIMITER +
                "animals: " + String.format("%7d", island.getAnimalCount()) + BIG_COLUMN_DELIMITER +
                "plants:" + String.format("%7d", island.getPlantCount());
    }

    private String getAnimalSummaryHeader() {
        StringBuilder animalSummaryHeader = new StringBuilder();
        for (AnimalType animalType : AnimalType.values()) {
            Class<? extends Animal> animalClazz = animalType.getAnimalClass();
            String className = animalClazz.getSimpleName();
            animalSummaryHeader.append(String.format("%-11s", className)).append(SMALL_COLUMN_DELIMITER);
        }
        return animalSummaryHeader.toString();
    }

    private String getAnimalSummaryInfo() {
        StringBuilder animalSummaryInfo = new StringBuilder();
        for (AnimalType animalType : AnimalType.values()) {
            Class<? extends Animal> animalClazz = animalType.getAnimalClass();
            int count = island.getAnimalCount(animalClazz);
            animalSummaryInfo.append(String.format("%-11d", count)).append(SMALL_COLUMN_DELIMITER);
        }
        return animalSummaryInfo.toString();
    }

    private String getRandomAnimalInfo() {
        if (randomAnimal == null) return "";

        String animalName = randomAnimal.getName();
        int posX = randomAnimal.getPositionX();
        int posY = randomAnimal.getPositionY();

        boolean isAlive = randomAnimal.isAlive();
        if (isAlive) {
            int saturationPercent = randomAnimal.getSaturationPercent();
            return String.format(ANIMAL_ALIVE_INFO_FORMAT, animalName, posX, posY, saturationPercent, island.getRandomAnimalActions());
        } else {
            String cause = randomAnimal.getCauseOfDeath();
            return String.format(ANIMAL_DEAD_INFO_FORMAT, animalName, posX, posY, cause);
        }
    }

    @Override
    public void setShowAnimal(boolean showAnimal) {
        this.showAnimal = showAnimal;
    }

    @Override
    public void setIsland(Island island) {
        this.island = island;
        randomAnimalClass = island.getRandomAnimalClass();
        randomAnimalClassName = randomAnimalClass.getSimpleName();
        randomAnimal = island.getRandomAnimal();

    }

    @Override
    public void updateRandomAnimal() {
        randomAnimal = island.getRandomAnimal();
    }

}
