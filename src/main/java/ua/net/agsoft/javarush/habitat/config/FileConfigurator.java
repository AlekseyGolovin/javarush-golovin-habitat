package ua.net.agsoft.javarush.habitat.config;

import org.json.simple.JSONObject;
import ua.net.agsoft.javarush.habitat.entity.island.Island;
import ua.net.agsoft.javarush.habitat.entity.organism.Organism;
import ua.net.agsoft.javarush.habitat.entity.organism.OrganismParameter;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.AnimalType;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.herbivore.*;
import ua.net.agsoft.javarush.habitat.entity.organism.animal.predator.*;
import ua.net.agsoft.javarush.habitat.entity.organism.plant.Plant;
import ua.net.agsoft.javarush.habitat.util.Util;
import ua.net.agsoft.javarush.habitat.util.UtilJSON;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FileConfigurator {

    private final Path configPath;

    private JSONObject rootObject;

    private final Map<Class<? extends Organism>, OrganismParameter> organismParameterMap = new HashMap<>();
    private final Map<Class<? extends Organism>, Map<Class<? extends Organism>, Integer>> eatenProbabilitiesMap = new HashMap<>();


    public FileConfigurator(Path configPath) {
        this.configPath = configPath;
    }

    public boolean isFileNotValid() {
        if (configPath == null) {
            return true;
        }
        if (!Files.isRegularFile(configPath)) {
            return true;
        }
        if (!Util.isJson(configPath)) {
            return true;
        }
        rootObject = getRootObject();
        if (rootObject == null) {
            return true;
        }
        return false;
    }

    private JSONObject getRootObject() {
        JSONConfigurator jsonConfigurator = new JSONConfigurator();
        jsonConfigurator.loadFromFile(configPath);
        return jsonConfigurator.getRootObject();
    }

    public Island configureIsland() {
        if (isFileNotValid()) {
            throw new RuntimeException("Configuration file is not valid");
        }
        JSONObject islandObject = (JSONObject) rootObject.get("Island");
        if (islandObject == null) {
            throw new RuntimeException("The JSON file is missing the 'Island' object.");
        }
        int width = UtilJSON.getIntFromObject(islandObject, "width", 1);
        int height = UtilJSON.getIntFromObject(islandObject, "height", 1);
        return Island.instanceOf(width, height);
    }

    public OrganismConfiguration configureOrganism() {
        if (isFileNotValid()) {
            throw new RuntimeException("Configuration file is not valid");
        }
        OrganismConfiguration organismConfiguration = new OrganismConfiguration();
        fillOrganismParameterMap();
        fillEatenProbabilitiesMap();
        organismConfiguration.setOrganismParameterMap(organismParameterMap);
        organismConfiguration.setEatenProbabilitiesMap(eatenProbabilitiesMap);
        return organismConfiguration;
    }

    private void fillEatenProbabilitiesMap() {
        for (AnimalType animalType : AnimalType.values()) {
            fillEatenProbabilitiesMap(animalType.getAnimalClass());
        }
//        fillEatenProbabilitiesMap(Wolf.class);
//        fillEatenProbabilitiesMap(Boa.class);
//        fillEatenProbabilitiesMap(Fox.class);
//        fillEatenProbabilitiesMap(Bear.class);
//        fillEatenProbabilitiesMap(Eagle.class);
//        fillEatenProbabilitiesMap(Horse.class);
//        fillEatenProbabilitiesMap(Deer.class);
//        fillEatenProbabilitiesMap(Rabbit.class);
//        fillEatenProbabilitiesMap(Mouse.class);
//        fillEatenProbabilitiesMap(Goat.class);
//        fillEatenProbabilitiesMap(Sheep.class);
//        fillEatenProbabilitiesMap(Boar.class);
//        fillEatenProbabilitiesMap(Buffalo.class);
//        fillEatenProbabilitiesMap(Duck.class);
//        fillEatenProbabilitiesMap(Caterpillar.class);
    }

    private void fillEatenProbabilitiesMap(Class<? extends Organism> eatingClazz) {
        Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap = new HashMap<>();
        for (AnimalType animalType : AnimalType.values()) {
            fillEatenProbabilitiesMap(eatingClazz, animalType.getAnimalClass(), itemEatenProbabilitiesMap);
        }
//        fillEatenProbabilitiesMap(eatingClazz, Wolf.class, itemEatenProbabilitiesMap);
//        fillEatenProbabilitiesMap(eatingClazz, Boa.class, itemEatenProbabilitiesMap);
//        fillEatenProbabilitiesMap(eatingClazz, Fox.class, itemEatenProbabilitiesMap);
//        fillEatenProbabilitiesMap(eatingClazz, Bear.class, itemEatenProbabilitiesMap);
//        fillEatenProbabilitiesMap(eatingClazz, Eagle.class, itemEatenProbabilitiesMap);
//        fillEatenProbabilitiesMap(eatingClazz, Horse.class, itemEatenProbabilitiesMap);
//        fillEatenProbabilitiesMap(eatingClazz, Deer.class, itemEatenProbabilitiesMap);
//        fillEatenProbabilitiesMap(eatingClazz, Rabbit.class, itemEatenProbabilitiesMap);
//        fillEatenProbabilitiesMap(eatingClazz, Mouse.class, itemEatenProbabilitiesMap);
//        fillEatenProbabilitiesMap(eatingClazz, Goat.class, itemEatenProbabilitiesMap);
//        fillEatenProbabilitiesMap(eatingClazz, Sheep.class, itemEatenProbabilitiesMap);
//        fillEatenProbabilitiesMap(eatingClazz, Boar.class, itemEatenProbabilitiesMap);
//        fillEatenProbabilitiesMap(eatingClazz, Buffalo.class, itemEatenProbabilitiesMap);
//        fillEatenProbabilitiesMap(eatingClazz, Duck.class, itemEatenProbabilitiesMap);
//        fillEatenProbabilitiesMap(eatingClazz, Caterpillar.class, itemEatenProbabilitiesMap);
        fillEatenProbabilitiesMap(eatingClazz, Plant.class, itemEatenProbabilitiesMap);
        eatenProbabilitiesMap.put(eatingClazz, itemEatenProbabilitiesMap);
    }

    private void fillEatenProbabilitiesMap(Class<? extends Organism> eatingClazz,
                                           Class<? extends Organism> edibleClazz,
                                           Map<Class<? extends Organism>, Integer> itemEatenProbabilitiesMap) {
        String eatingClassName = eatingClazz.getSimpleName();
        String edibleClassName = edibleClazz.getSimpleName();
        JSONObject itemObject = (JSONObject) rootObject.get(eatingClassName);
        JSONObject itemEatenProbabilities = (JSONObject) itemObject.get("EatenProbabilities");
        int eatenProbability = UtilJSON.getIntFromObject(itemEatenProbabilities, edibleClassName, 0);
        itemEatenProbabilitiesMap.put(edibleClazz, eatenProbability);
    }

    private void fillOrganismParameterMap() {
        for (AnimalType animalType : AnimalType.values()) {
            fillOrganismParameterMap(animalType.getAnimalClass());
        }
//        fillOrganismParameterMap(Wolf.class);
//        fillOrganismParameterMap(Boa.class);
//        fillOrganismParameterMap(Fox.class);
//        fillOrganismParameterMap(Bear.class);
//        fillOrganismParameterMap(Eagle.class);
//        fillOrganismParameterMap(Horse.class);
//        fillOrganismParameterMap(Deer.class);
//        fillOrganismParameterMap(Rabbit.class);
//        fillOrganismParameterMap(Mouse.class);
//        fillOrganismParameterMap(Goat.class);
//        fillOrganismParameterMap(Sheep.class);
//        fillOrganismParameterMap(Boar.class);
//        fillOrganismParameterMap(Buffalo.class);
//        fillOrganismParameterMap(Duck.class);
//        fillOrganismParameterMap(Caterpillar.class);
        fillOrganismParameterMap(Plant.class);
    }

    private void fillOrganismParameterMap(Class<? extends Organism> clazz) {
        String className = clazz.getSimpleName();
        JSONObject itemObject = (JSONObject) rootObject.get(className);
        JSONObject itemOrganismParameter = (JSONObject) itemObject.get("OrganismParameter");
        double weight = UtilJSON.getDoubleFromObject(itemOrganismParameter, "Weight", 1.0);
        double foodRequired = UtilJSON.getDoubleFromObject(itemOrganismParameter, "FoodRequired", 1.0);
        int maxSteps = UtilJSON.getIntFromObject(itemOrganismParameter, "MaxSteps", 1);
        int maxCountPerCell = UtilJSON.getIntFromObject(itemOrganismParameter, "MaxCountPerCell", 1);
        organismParameterMap.put(clazz, new OrganismParameter(weight, maxCountPerCell, maxSteps, foodRequired));
    }
}
