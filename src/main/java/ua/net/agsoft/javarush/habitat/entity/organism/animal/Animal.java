package ua.net.agsoft.javarush.habitat.entity.organism.animal;

import ua.net.agsoft.javarush.habitat.config.OrganismConfiguration;
import ua.net.agsoft.javarush.habitat.entity.island.Cell;
import ua.net.agsoft.javarush.habitat.entity.island.Island;
import ua.net.agsoft.javarush.habitat.entity.organism.Edible;
import ua.net.agsoft.javarush.habitat.entity.organism.Organism;
import ua.net.agsoft.javarush.habitat.entity.organism.plant.Plant;

import java.util.concurrent.ThreadLocalRandom;

// implements Eatable, Moveable, Reproducible
public abstract class Animal extends Organism implements Moveable, Eatable, Reproducible, Edible {

    protected boolean alive = true;
    protected int action;
    protected int positionX;
    protected int positionY;
    protected double maxSaturation;
    protected double saturation;
    private String causeOfDeath;

    public static Animal createInstance(Class<? extends Animal> clazz) {
        try {
            // Создаьть используя конструктор по умолчанию для указанного класса
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract int getNum();

    public abstract String getName();

    protected double getStarvationCoefficient() {
        return 1.0;
    }

    protected int getChanceForReproduce() {
        return 30;
    }

    public abstract boolean canEatPlants();

    public boolean isAlive() {
        return alive;
    }

    public void die(Cell cell, String cause) {
        alive = false;
        cell.decAnimal(this);
        causeOfDeath = cause;

    }

    public String getCauseOfDeath() {
        return causeOfDeath;
    }

    public void depletion(Island island) {
        Cell cell = island.getCell(positionX, positionY);

        double starvation = maxSaturation * getStarvationCoefficient() / 100.0;
        saturation -= starvation;
        if (saturation < 0.0) {
            die(cell, "lack of food");
        }
    }

    public boolean decAction() {
        if (action <= 0) return false;
        action--;
        return true;
    }

    public boolean canAction() {
        return action > 0;
    }

    public void incAction() {
        action++;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int position) {
        positionX = position;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int position) {
        positionY = position;
    }

    public void setMaxSaturation(double maxSaturation) {
        this.maxSaturation = maxSaturation;
    }

    public void setSaturation(double saturation) {
        if (saturation < 0) {
            this.saturation = 0.0;
            return;
        }
        if (saturation > maxSaturation) {
            this.saturation = maxSaturation;
            return;
        }
        this.saturation = saturation;
    }

    @Override
    public void move(Island island) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        Class<? extends Animal> clazz = this.getClass();
        OrganismConfiguration organismConfiguration = island.getOrganismConfiguration();
        int maxSteps = organismConfiguration.getOrganismParameterMap().get(clazz).getMaxSteps();

        for (int step = 0; step < maxSteps; step++) {
            int direction = tlr.nextInt(4);
            int newPositionX = positionX;
            int newPositionY = positionY;
            switch (direction) {
                case 0 -> newPositionX++;
                case 1 -> newPositionY++;
                case 2 -> newPositionX--;
                case 3 -> newPositionY--;
                default -> newPositionX++;
            }

            if (island.canMove(this, newPositionX, newPositionY)) {
                island.getCell(positionX, positionY).decAnimal(this);
                if (!island.getCell(newPositionX, newPositionY).incAnimal(this)) {
                    throw new RuntimeException(clazz.getSimpleName() + " type overflow when move animal");
                }
                positionX = newPositionX;
                positionY = newPositionY;
            }
        }
    }


    public int getSaturationPercent() {
        if (maxSaturation == 0.0) {
            return 100;
        }
        return (int) (100.0 * saturation / maxSaturation);
    }

    @Override
    public boolean eat(Island island) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        boolean isSelected = this == island.getRandomAnimal();

        boolean canFindFood = true;
        boolean isFoodPresent = false;

        Cell cell = island.getCell(positionX, positionY);

        while (canFindFood) {
            canFindFood = false;

            if (canEatPlants()) {
                // Пытаемся найти траву
                double plantCount = cell.getPlantCount();
                if (plantCount > 0) {
                    isFoodPresent = true;

                    if (isSelected) {
                        island.addRandomAnimalAction("I found a " + Plant.class.getSimpleName() + ". ");
                    }

                    int eatChance = island.getOrganismConfiguration().getEatenProbabilitiesMap()
                            .get(this.getClass()).get(Plant.class);
                    int chance = tlr.nextInt(100);
                    if (chance < eatChance) {

                        if (isSelected) {
                            island.addRandomAnimalAction("And ate it. ");
                        }

                        double weight = island.getOrganismConfiguration().getOrganismParameterMap()
                                .get(Plant.class).getWeight();
                        if (plantCount < weight) {
                            weight = plantCount;
                        }
                        double eaten = incSaturation(weight);
                        cell.decPlant(eaten);
                    } else {
                        if (isSelected) {
                            island.addRandomAnimalAction("But " + Plant.class.getSimpleName() + " was too hard. ");
                        }
                    }
                }
            }

            if (!isFoodPresent) {
                // Пытаемся найти другое животное
                //System.out.println("Пытаемся найти другое животное");
                Animal foodAnimal = island.getFood(this);
                if (foodAnimal != null) {

                    if (isSelected) {
                        island.addRandomAnimalAction("I found a " + foodAnimal.getClass().getSimpleName() + ". ");
                    }

                    isFoodPresent = true;

                    int eatChance = island.getOrganismConfiguration().getEatenProbabilitiesMap()
                            .get(this.getClass()).get(foodAnimal.getClass());
                    int chance = tlr.nextInt(100);
                    if (chance < eatChance) {

                        if (isSelected) {
                            island.addRandomAnimalAction("And ate it. ");
                        }

                        foodAnimal.bangBang(island);
                        double weight = island.getOrganismConfiguration().getOrganismParameterMap()
                                .get(foodAnimal.getClass()).getWeight();
                        incSaturation(weight);
                    } else {
                        if (isSelected) {
                            island.addRandomAnimalAction("But I couldn't catch it. ");
                        }
                    }
                }
            }
        }

        return isFoodPresent;
    }

    private double incSaturation(double weight) {
        double hunger = maxSaturation - saturation;
        if (hunger < weight) {
            saturation = maxSaturation;
            return hunger;
        } else {
            saturation += weight;
            if (saturation > maxSaturation) {
                saturation = maxSaturation;
            }
            return weight;
        }
    }

    @Override
    public boolean reproduce(Island island) {

        boolean isSelected = this == island.getRandomAnimal();

        Animal pairAnimal = island.getPair(this);
        if (pairAnimal == null) {
            return false;
        }

        if (isSelected) {
            island.addRandomAnimalAction("I found her. ");
        }

        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        int chance = tlr.nextInt(100);
        if (chance > getChanceForReproduce()) {
            if (isSelected) {
                island.addRandomAnimalAction("But we couldn't have a baby. ");
            }

            return true;
        }


        Class<? extends Animal> clazz = this.getClass();
        Animal childAnimal = Animal.createInstance(clazz);
        childAnimal.setWeight(weight);
        childAnimal.setMaxSaturation(maxSaturation);
        childAnimal.setSaturation(maxSaturation * 0.1);
        childAnimal.setPositionX(positionX);
        childAnimal.setPositionY(positionY);
        Cell cell = island.getCell(positionX, positionY);
        if (!cell.incAnimal(childAnimal)) {
            throw new RuntimeException(clazz.getSimpleName() + " type overflow");
        }
        island.getAnimals().add(childAnimal);
        pairAnimal.decAction();
        return true;
    }

    @Override
    public double bangBang(Island island) {
        Cell cell = island.getCell(positionX, positionY);
        die(cell, "I was eaten");

        Class<? extends Animal> clazz = this.getClass();
        double weight = island.getOrganismConfiguration().getOrganismParameterMap().get(clazz).getWeight();
        return weight;
    }
}
