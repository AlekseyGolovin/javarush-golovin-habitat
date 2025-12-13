package ua.net.agsoft.javarush.habitat.simulation;

import ua.net.agsoft.javarush.habitat.entity.island.Cell;
import ua.net.agsoft.javarush.habitat.entity.island.Island;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.Animal;
import ua.net.agsoft.javarush.habitat.statistic.Statistic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class IslandSimulation {

    public void simulate(Island island) {
        int beat = 0;
        do {
            // TODO: Истощение запасов еды
            if (beat % 10 == 0) depletion(island);

            // TODO: Довить всем животным по одному действию
            if (beat % 1 == 0) incAction(island);

            // TODO: Сбор статистики. отдельным потоком.
            if (beat % 100 == 0) showStatistic(island, beat);

            // TODO: Симуляция действий. Отдельным потоком
            if (beat % 1 == 0) makeChoice(island);

            // TODO: Поток роста ростительности
            if (beat % 1 == 0) growPlants(island);

            beat++;
        } while (beat < 1000);
        showStatistic(island, beat);
    }

    private void depletion(Island island) {
        ArrayList<Animal> animals = island.getAnimals();
        for (Animal animal : animals) {
            if (!animal.isAlive()) continue;
            Cell cell = island.getCell(animal.getPositionX(), animal.getPositionY());
            animal.depletion(cell);
        }
        animals.removeIf(animal -> !animal.isAlive());
    }

    private void incAction(Island island) {
        ArrayList<Animal> animals = island.getAnimals();
        for (Animal animal : animals) {
            if (!animal.isAlive()) continue;
            animal.incAction();
        }
    }

    private void showStatistic(Island island, int beat) {
        Statistic.showInConsole(island, beat);
    }

    private void makeChoice(Island island) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        ArrayList<Animal> animals = new ArrayList<>();
        //ArrayList<Animal> animals = island.getAnimals();


        animals.addAll(island.getAnimals());

        Iterator<Animal> it = animals.iterator();
        while (it.hasNext()) {
            Animal animal = it.next();
            Class<? extends Animal> clazz = animal.getClass();

            if (!animal.isAlive()) continue;

            //System.out.println(animal);

            while (animal.decAction()) {
                int saturationPercent = animal.getSaturationPercent();
                int chanceToEat = 100 - saturationPercent;
                int chanceToReproduce = chanceToEat + saturationPercent / 2;

                int chance = tlr.nextInt(100);

                if (chance <= chanceToEat) {
                    // Хочет кушать
                    //System.out.println("Хочет кушать");

                    if (animal.canEatPlants()) {
                        // Пытаемся найти траву


                    } else {
                        // Пытаемся найти другое животное


                    }
                } else {
                    if (chance <= chanceToReproduce) {

                        //System.out.println("Хочет размножаться");

                        if (!animal.reproduce(island)) {
                            //System.out.println("Не может размножаться");
                            animal.move(island);
                        }
                    } else {
                        //System.out.println("Хочет ходить");
                        animal.move(island);
                    }
                }


                //100 - %насыщения = вероятность для кушать
                // Оставшееся поделить пополам


                // Сделать выбор
                // Если сытость < 50% есть
                // Если не нашли еду, ходить

                // Размножение только если сытость Ю 50%
                // Ненашли пару, ходить


            }
        }
    }

    private void growPlants(Island island) {
        island.growPlants();
    }
}
