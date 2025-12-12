package ua.net.agsoft.javarush.habitat.entity.organism;

public class OrganismParameter {

    private double weight;
    private double foodRequired;
    private int maxSteps;
    private int maxCountPerCell;

    public OrganismParameter(double weight, int maxCountPerCell, int maxSteps, double foodRequired) {
        this.weight = weight;
        this.foodRequired = foodRequired;
        this.maxSteps = maxSteps;
        this.maxCountPerCell = maxCountPerCell;
    }

    public double getWeight() {
        return weight;
    }

    public double getFoodRequired() {
        return foodRequired;
    }

    public int getMaxSteps() {
        return maxSteps;
    }

    public int getMaxCountPerCell() {
        return maxCountPerCell;
    }
}
