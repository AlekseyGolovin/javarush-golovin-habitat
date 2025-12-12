package ua.net.agsoft.javarush.habitat.simulation;

import ua.net.agsoft.javarush.habitat.entity.island.Island;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.Animal;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.Moveable;

import java.util.ArrayList;

public class IslandSimulation {

    public void simulate(Island island) {
        int beat = 0;
        do{
            // TODO: Истощение запасов еды
            if (beat % 10 == 0) depletion(island);

            // TODO: Довить всем животным по одному действию
            if (beat % 1 == 0) incAction(island);

            // TODO: Сбор статистики. отдельным потоком.
            if (beat % 100 == 0) showStatistic(island);

            // TODO: Симуляция действий. Отдельным потоком
            if (beat % 1 == 0) makeChoice(island);

            // TODO: Поток роста ростительности
            if (beat % 1 == 0) growPlants(island);

            beat++;
        } while(beat < 1000);
    }

    private void depletion(Island island) {
        ArrayList<Animal> animals = island.getAnimals();
        for (Animal animal : animals) {
            if (!animal.isAlive()) continue;
            animal.depletion();
        }
    }

    private void incAction(Island island) {
        ArrayList<Animal> animals = island.getAnimals();
        for (Animal animal : animals) {
            if (!animal.isAlive()) continue;
            animal.incAction();
        }
    }

    private void showStatistic(Island island) {

    }

    private void makeChoice(Island island) {
        ArrayList<Animal> animals = island.getAnimals();
        for (Animal animal : animals) {

            if (!animal.isAlive()) continue;

            while (animal.decAction()) {
                //100 - %насыщения = вероятность для кушать
                // Оставшееся поделить пополам




                // Сделать выбор
                // Если сытость < 50% есть
                // Если не нашли еду, ходить

                // Размножение только если сытость Ю 50%
                // Ненашли пару, ходить

                if (animal != null && animal instanceof Moveable) {
                    animal.move(island);
                }
            }
        }
    }

    private void growPlants(Island island) {
        island.growPlants();
    }
}
