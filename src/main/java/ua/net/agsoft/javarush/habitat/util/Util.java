package ua.net.agsoft.javarush.habitat.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Util {
    // Let's get rid of the Java or JAR file binding
    private static Path getLocation(URI uri) {
        System.out.println("uri: " + uri);
        Path path = Paths.get(uri);
        System.out.println("path: " + path);
        if (Files.isRegularFile(path)) {
            System.out.println("path.getParent " + path.getParent().toString());
            return path.getParent();
        } else {
            System.out.println("path: " + path);
            return path;
        }
    }


    public static Path getLocation(Class<?> clazz) throws URISyntaxException {
        // Let's get rid of the operating system binding
        URI uri = clazz.getProtectionDomain().getCodeSource().getLocation().toURI();
        return getLocation(uri);
    }

    public static String getFileExtension(Path path) {
        Path fileNamePath = path.getFileName();
        if (fileNamePath == null) {
            return "";
        }
        String fileName = fileNamePath.toString();
        int lastDotIndex = fileName.lastIndexOf('.');

        // Проверка, есть ли точка и не является ли она первым символом
        if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1).toLowerCase();
        }
        return "";
    }

    public static boolean isJson(Path path) {
        return "json".equals(getFileExtension(path));
    }

    public static boolean isXml(Path path) {
        // Часто используются расширения xml, xsd, xsl, xhtml, но .xml самое общее
        return "xml".equals(getFileExtension(path));
    }

    public static <T> T getRandom(List<T> list) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();
        if (list.isEmpty()) return null;
        int size = list.size();
        int index = tlr.nextInt(size);
        return list.get(index);

    }


}
