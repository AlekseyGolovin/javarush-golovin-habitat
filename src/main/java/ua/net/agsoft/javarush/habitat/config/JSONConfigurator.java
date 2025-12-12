package ua.net.agsoft.javarush.habitat.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JSONConfigurator {
    private JSONObject rootObject;

    public void loadFromFile(Path configPath) {
        String jsonStr;
        try {
            jsonStr = Files.readString(configPath);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + configPath + ". Check the path and permissions.");
        }

        JSONParser parser = new JSONParser();
        try {
            rootObject = (JSONObject) parser.parse(jsonStr);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing JSON file. Check the syntax.");
        }
    }

    public JSONObject getRootObject() {
        return rootObject;
    }

}
