package ua.net.agsoft.javarush.habitat.simulation;

import ua.net.agsoft.javarush.habitat.entity.island.Island;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.Animal;
import ua.net.agsoft.javarush.habitat.statistic.ConsoleStatistic;
import ua.net.agsoft.javarush.habitat.statistic.Statistic;
import ua.net.agsoft.javarush.habitat.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class IslandSimulation {

    private Island island;
    private Animal randomAnimal;


    public void simulate(Island island, Class monitoredClass, boolean showAnimal) {

        this.island = island;
        island.setRandomAnimalClass(monitoredClass);
        island.selectRandomAnimal();
        randomAnimal = island.getRandomAnimal();

        Statistic statistic = new ConsoleStatistic();
        statistic.setIsland(this.island);
        statistic.setShowAnimal(showAnimal);

        int beat = 0;
        statistic.show(0);
        do {
            // TODO: Поток роста ростительности
            growPlants();

            // TODO: Добавить всем животным по одному действию
            incAction();

            // TODO: Симуляция действий. Отдельным потоком
            island.clearRandomAnimalAction();
            makeChoice();

            // TODO: Истощение запасов еды
            depletion();

            // TODO: Сбор статистики. отдельным потоком.
            statistic.show(beat + 1);

            if (randomAnimal == null || !randomAnimal.isAlive()) {
                island.selectRandomAnimal();
                randomAnimal = island.getRandomAnimal();
                statistic.updateRandomAnimal();
            }

            beat++;
        } while (randomAnimal != null && beat < 200);

        statistic.showExtinction();
    }

    private void growPlants() {
        island.growPlants();
    }

    private void depletion() {
        ArrayList<Animal> animals = island.getAnimals();
        for (Animal animal : animals) {
            if (!animal.isAlive()) continue;
            animal.depletion(island);
        }
        animals.removeIf(animal -> !animal.isAlive());
    }

    private void incAction() {
        ArrayList<Animal> animals = island.getAnimals();
        for (Animal animal : animals) {
            if (!animal.isAlive()) continue;
            animal.incAction();
        }
    }

    private void makeChoice() {
        ArrayList<Animal> animals = new ArrayList<>(island.getAnimals());
        for (Animal animal : animals) {
            if (!animal.isAlive()) continue;
            makeChoiceForAnimal(animal);
        }
    }

    private void makeChoiceForAnimal(Animal animal) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        while (animal.decAction()) {

            boolean isSelected = randomAnimal != null && animal == randomAnimal;

            int saturationPercent = animal.getSaturationPercent();
            int chanceToEat = 100 - saturationPercent;

            int chanceToReproduce = chanceToEat + saturationPercent / 10;
            int maxSteps = island.getOrganismConfiguration().getOrganismParameterMap()
                    .get(animal.getClass()).getMaxSteps();
            if (maxSteps == 0) {
                chanceToReproduce = 100;
            }

            int chance = tlr.nextInt(100);

            if (chance <= chanceToEat) {
                //System.out.println("Хочет есть");

                if (isSelected) {
                    island.addRandomAnimalAction("I wanted to eat. ");
                }

                if (!animal.eat(island)) {
                    if (isSelected) {
                        island.addRandomAnimalAction("But there is no food here. ");
                    }
                    animal.move(island);
                }
            } else {
                if (chance <= chanceToReproduce) {
                    if (isSelected) {
                        island.addRandomAnimalAction("I wanted to reproduce. ");
                    }
                    if (!animal.reproduce(island)) {
                        if (isSelected) {
                            island.addRandomAnimalAction("But there are no animals of my kind here. ");
                        }
                        animal.move(island);
                    }
                } else {
                    if (isSelected) {
                        island.addRandomAnimalAction("I wanted to walk. ");
                    }
                    animal.move(island);
                }
            }
        }
    }
}
