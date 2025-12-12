package ua.net.agsoft.javarush.habitat.config;


import ua.net.agsoft.javarush.habitat.entity.organism.Organism;
import ua.net.agsoft.javarush.habitat.entity.organism.OrganismParameter;


import java.util.HashMap;
import java.util.Map;

public class OrganismConfiguration {

    private Map<Class<? extends Organism>, OrganismParameter> organismParameterMap = new HashMap<>();
    private Map<Class<? extends Organism>, Map<Class<? extends Organism>, Integer>> eatenProbabilitiesMap = new HashMap<>();

    public OrganismParameter getOrganismParameter(Class<? extends Organism> clazz) {
        OrganismParameter params = organismParameterMap.get(clazz);
        if (params == null) {
            throw new IllegalStateException("Missing class parameters: " + clazz.getSimpleName());
        }
        return params;
    }


    public void setOrganismParameterMap(Map<Class<? extends Organism>, OrganismParameter> organismParameterMap) {
        this.organismParameterMap = organismParameterMap;
    }

    public void setEatenProbabilitiesMap(Map<Class<? extends Organism>, Map<Class<? extends Organism>, Integer>> eatenProbabilitiesMap) {
        this.eatenProbabilitiesMap = eatenProbabilitiesMap;
    }

    public Map<Class<? extends Organism>, OrganismParameter> getOrganismParameterMap() {
        return organismParameterMap;
    }

    public Map<Class<? extends Organism>, Map<Class<? extends Organism>, Integer>> getEatenProbabilitiesMap() {
        return eatenProbabilitiesMap;
    }
}
