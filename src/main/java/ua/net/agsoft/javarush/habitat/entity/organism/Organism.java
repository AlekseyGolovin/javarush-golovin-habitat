package ua.net.agsoft.javarush.habitat.entity.organism;

import ua.net.agsoft.javarush.habitat.config.OrganismConfiguration;
// implements Edible
public abstract class Organism{

    //private OrganismParameter organismParameter;



    protected double weight;

    //private int maxCountPerCell;



    public Organism() {

        //OrganismParameter getOrganismParameter = OrganismConfiguration.getOrganismParameter(this.getClass());


        //this.organismParameter =
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }



}
