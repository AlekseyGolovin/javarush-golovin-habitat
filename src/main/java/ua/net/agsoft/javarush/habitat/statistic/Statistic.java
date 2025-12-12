package ua.net.agsoft.javarush.habitat.statistic;

import ua.net.agsoft.javarush.habitat.entity.island.Island;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.Animal;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.AnimalType;

public class Statistic {

    private static void clearScreen() {
        try{
            String operatingSystem = System.getProperty("os.name");
            if(operatingSystem.contains("Windows")){
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static void showInConsole(Island island, int beat){
        String delimeter = "-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
        //clearScreen();

        String summary = "beat "+String.format("%7d", beat) +"     |     " +
                "animals: "+String.format("%7d", island.getAnimalCount()) +"     |     " +
                "plants:"+String.format("%7d", island.getPlantCount());
        String animalSummaryHeader = "";
        for (AnimalType animalType : AnimalType.values()) {
            Class<? extends Animal> animalClazz = animalType.getAnimalClass();
            //int islandCount = getAnimalCountFromList(animalClazz);
            //int cellsCount = getAnimalCountFromCells(animalClazz);
            String className = animalClazz.getSimpleName();
            //String animalStatistic = className + ": [" + islandCount + "][" + cellsCount + "]\n";
            animalSummaryHeader += String.format("%-11s", className) +" | ";
        }

        String animalSummaryInfo = "";
        for (AnimalType animalType : AnimalType.values()) {
            Class<? extends Animal> animalClazz = animalType.getAnimalClass();
            int count = island.getAnimalCount(animalClazz);
            //int cellsCount = getAnimalCountFromCells(animalClazz);
            //String className = animalClazz.getSimpleName();
            //String animalStatistic = className + ": [" + islandCount + "][" + cellsCount + "]\n";
            animalSummaryInfo += String.format("%-11d", count) +" | ";
        }

        System.out.println(delimeter);
        System.out.println(summary);
        System.out.println(delimeter);
        System.out.println(animalSummaryHeader);
        System.out.println(animalSummaryInfo);
        System.out.println(delimeter);
    }



}
