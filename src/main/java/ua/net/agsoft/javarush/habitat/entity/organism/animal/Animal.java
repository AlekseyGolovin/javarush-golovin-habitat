package ua.net.agsoft.javarush.habitat.entity.organism.animal;

import ua.net.agsoft.javarush.habitat.entity.organism.Organism;

// implements Eatable, Moveable, Reproducible
public abstract class Animal extends Organism {

    protected int positionX;
    protected int positionY;

    protected double maxSaturation;
    protected double saturation;

    public static Animal createInstance(Class<? extends Animal> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
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


    public void setPositionX(int position) {
        positionX = position;
    }

    public void setPositionY(int position) {
        positionY = position;
    }
}
