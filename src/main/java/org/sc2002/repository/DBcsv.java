package org.sc2002.repository;

import org.sc2002.entity.LineMapper;
import org.sc2002.entity.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * DBcsv supports read and save operation to csv files. To work with Student and Staff list for initialization
 */
public class DBcsv {

    /**
     * comma separator for csv file
     */
    public static final String SEPARATOR = ",";

    /**
     * Reads entities from a csv file
     * @param filename file name in name.csv
     * @param mapper lambda function to map csv fields into entity constructor
     * @return List of entities read from csv file
     */
    public static <Entity> List<Entity> read(String filename, LineMapper<Entity> mapper) throws IOException {
        return Files.lines(Paths.get(filename))
                .map(line -> line.split(SEPARATOR))
                .map(mapper::mapLine)
                .collect(Collectors.toList());
    }

    /**
     * Saves entities to a csv file
     * @param filename file name in name.csv
     * @param items List of entities to be saved
     * @return lambda function to map csv fields into entity constructor
     */
    public static <Entity> void save(String filename, List<Entity> items, Function<Entity, String> formatter) throws IOException {
        List<String> lines = items.stream()
                .map(formatter)
                .collect(Collectors.toList());
        Files.write(Paths.get(filename), lines);
    }
}
